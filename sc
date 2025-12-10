alter session set nls_date_format = 'DD-MM-YYYY HH24:MI:SS';

set serveroutput on;

create or replace procedure P_POLICY_EXP_DATE_FIX as
v_product_code varchar2(100);
v_policy_id number;
v_policy_no varchar2(100);
v_quote_no varchar2(100);
v_eff_date date;
v_exp_date date;
v_field36 varchar2(100);
v_source_system_name varchar2(100);
v_status_id number;
v_contract_status_code number;
v_diff_days number;
v_crt_date date;
V_ERROR varchar2(100);
V_ERROR_CODE varchar2(100);

cursor c1 is  (select c.PRODUCT_CODE,a.policy_id, a.policy_no, a.quote_no, a.eff_date, a.exp_date,b.field36,b.source_system_name,A.STATUS_ID,a.contract_status_code,
TO_Number(a.exp_date-a.eff_date) diff_days
from generalsystem.T_POLICY_GENERAL a 
join generalsystem.T_SBI_POLICY_GENERAL b on b.policy_id=a.policy_id AND A.STATUS_ID=120 AND a.contract_status_code<>3
join generalsystem.T_PRODUCT_GENERAL c on c.product_id=a.product_id
where a.exp_date  = trunc(a.exp_date)
AND  TRUNC(A.EFF_DATE) > TRUNC(SYSDATE)
--AND  a.issue_date> sysdate-1 							--- Don't Enable now.
AND TO_Number(a.exp_date-a.eff_date) > 364);
--and a.RENEW_OPER_FLAG=0);

begin
    
    open c1;
    loop
    fetch c1 into v_product_code,v_policy_id,v_policy_no,v_quote_no,v_eff_date,v_exp_date,v_field36,v_source_system_name,v_status_id,v_contract_status_code,v_diff_days ;
    exit when c1%notfound;
    
        select TO_DATE(TO_CHAR(v_exp_date,'DD-MM-YYYY') || '23:59:59','DD-MM-YYYY HH24:MI:SS') into v_crt_date  from dual;
        
        update t_policy_general 
        set exp_date=v_crt_date
        where policy_id=v_policy_id;
        insert into T_SBI_EXP_TIMESTAMP_FIX(product_code,policy_id,policy_no,quote_no,eff_date,exp_date,field36, source_system_name,status_id,contract_status_code,diff_days,CRT_EXP_DATE)
        values(v_product_code, v_policy_id, v_policy_no, v_quote_no, v_eff_date, v_exp_date, v_field36, v_source_system_name, v_status_id, v_contract_status_code, v_diff_days,v_crt_date);
        
    end loop;
	
    CLOSE C1;
	
    EXCEPTION WHEN OTHERS THEN
        V_ERROR        := SQLERRM;
        V_ERROR_CODE   := SQLCODE;
         
		 insert into T_SBI_EXP_TIMESTAMP_FIX(product_code,policy_id,policy_no,quote_no,eff_date,exp_date,field36, source_system_name,status_id,contract_status_code,diff_days,CRT_EXP_DATE,ERR_CODE ,ERR_MSG)
        values(v_product_code, v_policy_id, v_policy_no, v_quote_no, v_eff_date, v_exp_date, v_field36, v_source_system_name, v_status_id, v_contract_status_code, v_diff_days,v_crt_date,V_ERROR,V_ERROR_CODE);


end P_POLICY_EXP_DATE_FIX;




begin
P_POLICY_EXP_DATE_FIX
end;

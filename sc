create or replace PROCEDURE P_POLICY_EXP_DATE_CHECK AS
cursor c_policy_id is SELECT  a.policy_id,a.exp_date
FROM generalsystem.T_POLICY_GENERAL a 
WHERE a.exp_date > SYSDATE - 1
AND a.exp_date = TRUNC(a.exp_date) and policy_id in (9456944,9457308,9456158,9456017);
 
begin
     for i in c_policy_id loop
	 
        UPDATE  generalsystem.t_policy_general SET exp_date = to_date('25-11-2025 23:59:59','dd-mm-rrrr hh24:mi:ss')  WHERE  policy_id  IN (i.policy_id);
        --dbms_output.put_line(i.case_id ||'-'|| v_order_id);
     end loop;
end P_POLICY_EXP_DATE_CHECK;

begin
P_POLICY_EXP_DATE_CHECK;
end;
DROP PROCEDURE P_POLICY_EXP_DATE_CHECK;
end;

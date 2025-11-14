<%@ page import="com.ebao.gs.pol.core.nb.framework.FlowConstant" %>
<%@ page import=" com.ebao.gs.pol.core.nb.framework.FlowContext" %>
<%@ page import=" com.ebao.gs.pol.core.pc.ctrl.form.PageBean" %>
<%@ page import="com.ebao.gs.pol.prdt.ProductActionAPIConstant" %>
<%@ page import="com.ebao.gs.pol.prdt.ProductConstant" %>
<%@ page import="com.ebao.gs.pol.prdt.pc.PCConstant"%>
<%@ page import="com.ebao.gs.pol.prdt.pc.persistence.ExtendedPointFactory" %>
<%@ page import="com.ebao.gs.pub.SystemConstant" %>
<%@ page import="com.ebao.pub.wincony.core.CorePreferencesConstants" %>
<%@ page import="com.ebao.sbi.gs.pol.core.pub.bo.PolicyBO"%>
<%@ page import="com.ebao.gs.pol.core.pub.bo.InsuredBO"%>
<%@ page import="com.ebao.gs.pol.core.pub.bo.PolicyCtBO"%>
<%@ page import="com.ebao.gs.pol.core.pub.bo.PolicyCtAcceBO"%>
<%@ page import="com.ebao.gs.pol.core.pub.bo.PersonInsuredBO"%>
<%@ page import="com.ebao.gs.pol.core.pub.bo.BOFactory"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.*"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="com.ebao.sbi.gs.pub.js.dataexchange.DataExchangeSupport"%>
<%@ page import="java.io.PrintStream"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.math.MathContext"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>
<%@ page import="javax.servlet.http.HttpServletResponse"%>
<%@ page import="java.math.BigDecimal"%>
<%@ taglib uri="http://www.ebaotech.com/taglib/icp/ifoundation/pageconfig/2009" prefix="PageConfig" %>
<%@ taglib uri="http://www.ebaotech.com/taglib/icp/ifoundation/strres/2009" prefix="StrRes" %>
<%@ taglib uri="http://www.ebaotech.com/taglib/icp/ifoundation/strres/2009" prefix="StrRes" %>
<%@ taglib
	uri="http://www.ebaotech.com/taglib/icp/ifoundation/field/2009"
	prefix="Field"%>		
<%
	FlowContext flowContext = (FlowContext) request.getSession().getAttribute(FlowConstant.FLOW_CONTEXT_KEY);
	int selfStatus = 0;
	String siDropDown= "";
   	String relationwithProp= "";
   	int totalInsuredCount = 0;
   	int covercount = 0;
	String hospMinSI= "";
	String hospMaxSI= "";
	String isNewborn= "";
	String isAggregate= "";
	String isOutpat= "";
	String isMaternity= "";
	String isCopayopt= ""; 
	String ciMinSI= "";		
	String ciMaxSI= ""; 
	String paMinSI= ""; 
	String paMaxSI= ""; 
	String hdcMinSI= "";
	String hdcMaxSI= ""; 
String hdcdur= "";
	BigDecimal siOfSelf = new BigDecimal(0.00);
	Long policy_Id=Long.parseLong(flowContext.getPolicyId());
	System.out.println("GSA001 policy_Id    :"+policy_Id);
	PolicyBO policyBO = (PolicyBO) BOFactory.loadPolicyBO(policy_Id);
	String typeOfProduct = (String) policyBO.getGenPolicyInfoBVO().getProperty("Type_of_Product_A_PO");
	String famCategory = (String) policyBO.getGenPolicyInfoBVO().getProperty("Family_Floater_Category_A_PO");

	System.out.println("GSA001 typeOfProduct    :"+typeOfProduct);
		System.out.println("GSA001 famCategory    :"+famCategory);

	List dynObj = new ArrayList();
		dynObj=policyBO.getDynamicObjects().get("GSA_Master_Policy_Details");
		if(dynObj != null)
		{		
			for(Iterator gsadynObj = dynObj.iterator();gsadynObj.hasNext();)
			{
				Map gsadynObjValue = (Map)gsadynObj.next();
				hospMinSI = (String)gsadynObjValue.get("Hospitalization_minimum_SI_Limit");
				System.out.println("GSA001 hospMinSI    :"+hospMinSI);
				hospMaxSI = (String)gsadynObjValue.get("Hospitalization_maximum_SI_Limit");
				System.out.println("GSA001 hospMaxSI    :"+hospMaxSI);
				isNewborn = (String)gsadynObjValue.get("IsNewbornopted");
								System.out.println("GSA001 isNewborn    :"+isNewborn);

				isAggregate = (String)gsadynObjValue.get("IsAggregatedeductibleopted");
								System.out.println("GSA001 isAggregate    :"+isAggregate);

				isOutpat = (String)gsadynObjValue.get("IsOutpatientexpenseopted");
								System.out.println("GSA001 isOutpat    :"+isOutpat);

				isMaternity = (String)gsadynObjValue.get("IsMaternityopted");
								System.out.println("GSA001 isMaternity    :"+isMaternity);

				isCopayopt = (String)gsadynObjValue.get("Iscopaymentopted");
								System.out.println("GSA001 isCopayopt    :"+isCopayopt);
				ciMinSI = (String)gsadynObjValue.get("CI_minimum_SI_limit");
				System.out.println("GSA001 ciMinSI    :"+ciMinSI);
				ciMaxSI = (String)gsadynObjValue.get("CI_maximum_SI_limit");
				System.out.println("GSA001 ciMaxSI    :"+ciMaxSI);				
				paMinSI = (String)gsadynObjValue.get("PA_minimum_SI_limit");
				System.out.println("GSA001 paMinSI    :"+paMinSI);
				paMaxSI = (String)gsadynObjValue.get("PA_maximum_SI_limit");
				System.out.println("GSA001 paMaxSI    :"+paMaxSI);
				
				hdcMinSI = (String)gsadynObjValue.get("HDC_minimum_SI_limit");
				System.out.println("GSA001 hdcMinSI    :"+hdcMinSI);
				hdcMaxSI = (String)gsadynObjValue.get("HDC_maximum_SI_limit");
				System.out.println("GSA001 hdcMaxSI    :"+hdcMaxSI);
				hdcdur = (String)gsadynObjValue.get("HDCDuration");
				System.out.println("GSA001 hdcdur    :"+hdcdur);

			}	
		} 
		
%>

<script language="JavaScript" type="text/JavaScript">
var relationshipPrposer ="0";
function setSActionGSA(str, obj)
{ 

	var RelationshipWithProposerASAN= '<%=request.getParameter("RelationshipWithProposerASAN")%>';
	var rowCountASAN = '<%=request.getParameter("rowCountASAN")%>';
	obj.rowCountASAN.value = rowCountASAN;

	
	  
}
function setSumInsuredPA(str,obj){
	var ctId = document.getElementById("_ctId").value; 
if (ctId =="900009643"){
	
	var relationshipPrposer= '<%=request.getParameter("RelationshipWithProposerASAN")%>';
	if (relationshipPrposer == '2'){
		updateSpousePA_SI();
	}
	else 
	{
		
	var  siOfCover_pa = oPage.getControl("PA_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].getValue();		
		var insval=0;
	
		if(siOfCover_pa==4){
			insval=100000;
		}		
		else if(siOfCover_pa==8){
			insval=200000; 
		}		
		else if(siOfCover_pa==12){
			insval=300000; 
		}
		else if(siOfCover_pa==16){
			insval=400000; 
		}
		else if(siOfCover_pa==20){
			insval=500000; 
		}else if(siOfCover_pa==21){
			insval=600000; 
		}
		else if(siOfCover_pa==22){
			insval=700000; 
		}
		else if(siOfCover_pa==23){
			insval=800000; 
		}
		else if(siOfCover_pa==24){
			insval=900000; 
		}
		else if(siOfCover_pa==25){
			insval=1000000; 
		}
		else if(siOfCover_pa==26){
			insval=1100000; 
		}
		else if(siOfCover_pa==27){
			insval=1200000; 
		}
		else if(siOfCover_pa==28){
			insval=1300000; 
		}
		else if(siOfCover_pa==29){
			insval=1400000; 
		}
		else if(siOfCover_pa==30){
			insval=1500000; 
		}
		else if(siOfCover_pa==31){
			insval=1600000; 
		}
		else if(siOfCover_pa==32){
			insval=1700000; 
		}
		else if(siOfCover_pa==33){
			insval=1800000; 
		}
		else if(siOfCover_pa==34){
			insval=1900000; 
		}
		else if(siOfCover_pa==35){
			insval=2000000; 
		}
		
var paMMinsi = "<%=paMinSI%>";
var paMMinsi1=parseInt(paMMinsi);	
var paMMaxsi = "<%=paMaxSI%>";
var paMMaxsi1=parseInt(paMMaxsi);	
var baseCoverSi1=parseInt(insval);
if(baseCoverSi1 < paMMinsi1 || baseCoverSi1 > paMMaxsi1){
	alert("SI should be within the range (Minimum "+paMMinsi1+" Maximum "+paMMaxsi1+") as defined in the Master Policy");
	oPage.getControl("PA_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue('');	
	return false;
}

else{
	var tabObj2 = oPage.getControl("PA_Benefit_Table");   
	var row = tabObj2.getTableRows();	
	if(baseCoverSi1 !=null && baseCoverSi1 !='')
	{
		for (var i = 0; i < row.length; i++)
			{
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Accidental death")
				{								
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',baseCoverSi1);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',baseCoverSi1);
				
				}
				if(nam == "PTD")
				{				
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',baseCoverSi1);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',baseCoverSi1);				
				}
				if(nam == "Funeral Expenses")
				{
				var funSI=baseCoverSi1*0.01;				
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',baseCoverSi1);				
				row[i].setValue('PolicyCtAcceBVO.InterestSi',funSI);				
				}
				
			}
		}
	}
	
	 
	}
	setSumInsuredPACT();
}
}



function updatePA_SI()
{
var pol_id = <%=policy_Id%>;
var whereclause = 'policy_id='+pol_id+'';
var count = getData("T_INSURED_LIST", "COUNT(INSURED_ID)", whereclause );
if(count > '1')
{
if(relationshipPrposer == '3' || relationshipPrposer == '4')
{

     var sql1 =  'FIELD17 in (select insured_id from t_insured_list where policy_id = ' + pol_id + ' and liability_status In (1,2)) and field1=1';
     var insId = getData("T_GS_RESERVE_TB_900000800", "FIELD17", sql1);			
	if (insId != null && insId != ''){
	var sql2 = "liability_status In (1,2) and ct_id = '900009643' and insured_id = '"+insId+"'";
	var siOfCover_pa = getData("T_POLICY_CT", "field10", sql2);	
	var insval=0;
    var siamount=0;	
		if(siOfCover_pa==4){
			insval=1;
			siamount=25000;
		}
		else if(siOfCover_pa==8){
			insval=2;
			siamount=50000;
		}
		else if(siOfCover_pa==12){
			insval=3;
			siamount=75000;
		}
		else if(siOfCover_pa==16){
			insval=4;
			siamount=100000;
		}
		else if(siOfCover_pa==20){
			insval=5;
			siamount=125000;
		}
		else if(siOfCover_pa==21){
			insval=6;
			siamount=150000;			
		}
		else if(siOfCover_pa==22){
			insval=7; 
			siamount=175000;
		}
		else if(siOfCover_pa==23){
			insval=8; 
			siamount=200000;
		}
		else if(siOfCover_pa==24){
			insval=9; 
			siamount=225000;
		}
		else if(siOfCover_pa==25){
			insval=10; 
			siamount=250000;
		}
		else if(siOfCover_pa==26){
			insval=11; 
			siamount=275000;
		}
		else if(siOfCover_pa==27){
			insval=12; 
			siamount=300000;
		}
		else if(siOfCover_pa==28){
			insval=13; 
			siamount=325000;
		}
		else if(siOfCover_pa==29){
			insval=14; 
			siamount=350000;
		}
		else if(siOfCover_pa==30){
			insval=15; 
			siamount=375000;
		}
		else if(siOfCover_pa==31){
			insval=16; 
			siamount=400000;
		}
		else if(siOfCover_pa==32){
			insval=17; 
			siamount=425000;
		}
		else if(siOfCover_pa==33){
			insval=18; 
			siamount=450000;
		}
		else if(siOfCover_pa==34){
			insval=19; 
			siamount=475000;
		}
		else if(siOfCover_pa==35){
			insval=20;
			siamount=500000;
		}			

	oPage.getControl("PA_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue(insval);	
	oPage.getControl("PA_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setReadOnly(true);
	
	var tabObj2 = oPage.getControl("PA_Benefit_Table");   
	var row = tabObj2.getTableRows();	
	if(siamount !=null && siamount !='')
	{
		for (var i = 0; i < row.length; i++)
			{
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Accidental death")
				{								
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',siamount);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',siamount);
				
				}
				if(nam == "PTD")
				{				
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',siamount);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',siamount);				
				}
				if(nam == "Funeral Expenses")
				{
				var funSI=siamount*0.01;				
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',siamount);				
				row[i].setValue('PolicyCtAcceBVO.InterestSi',funSI);				
				}
				
			}
		}
		
	
	document.getElementById("PA_Benefit_Details").style.display='none';
		
	
}
}
}
}


function updateSpousePA_SI()
{	
var pol_id = <%=policy_Id%>;
var relationshipPrposer= '<%=request.getParameter("RelationshipWithProposerASAN")%>';
var whereclause = 'policy_id='+pol_id+'';
var count = getData("T_INSURED_LIST", "COUNT(INSURED_ID)", whereclause );
if(count > '1')
{
if(relationshipPrposer == '2')
{

     var sql1 =  'FIELD17 in (select insured_id from t_insured_list where policy_id = ' + pol_id + ' and liability_status In (1,2)) and field1=1';
     var insId = getData("T_GS_RESERVE_TB_900000800", "FIELD17", sql1);			
	if (insId != null && insId != ''){
	var sql2 = "liability_status In (1,2) and ct_id = '900009643' and insured_id = '"+insId+"'";
	var siOfCover_pa = getData("T_POLICY_CT", "field10", sql2);	
	var insval=0;
    var siamount=0;	
			if(siOfCover_pa==4){
			insval=100000;
		}		
		else if(siOfCover_pa==8){
			insval=200000; 
		}		
		else if(siOfCover_pa==12){
			insval=300000; 
		}
		else if(siOfCover_pa==16){
			insval=400000; 
		}
		else if(siOfCover_pa==20){
			insval=500000; 
		}else if(siOfCover_pa==21){
			insval=600000; 
		}
		else if(siOfCover_pa==22){
			insval=700000; 
		}
		else if(siOfCover_pa==23){
			insval=800000; 
		}
		else if(siOfCover_pa==24){
			insval=900000; 
		}
		else if(siOfCover_pa==25){
			insval=1000000; 
		}
		else if(siOfCover_pa==26){
			insval=1100000; 
		}
		else if(siOfCover_pa==27){
			insval=1200000; 
		}
		else if(siOfCover_pa==28){
			insval=1300000; 
		}
		else if(siOfCover_pa==29){
			insval=1400000; 
		}
		else if(siOfCover_pa==30){
			insval=1500000; 
		}
		else if(siOfCover_pa==31){
			insval=1600000; 
		}
		else if(siOfCover_pa==32){
			insval=1700000; 
		}
		else if(siOfCover_pa==33){
			insval=1800000; 
		}
		else if(siOfCover_pa==34){
			insval=1900000; 
		}
		else if(siOfCover_pa==35){
			insval=2000000; 
		}
			var  siOfSpousePA = oPage.getControl("PA_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].getValue();
		if(siOfSpousePA==4){
			insvalSpouse=100000;
		}		
		else if(siOfSpousePA==8){
			insvalSpouse=200000; 
		}		
		else if(siOfSpousePA==12){
			insvalSpouse=300000; 
		}
		else if(siOfSpousePA==16){
			insvalSpouse=400000; 
		}
		else if(siOfSpousePA==20){
			insvalSpouse=500000; 
		}else if(siOfSpousePA==21){
			insvalSpouse=600000; 
		}
		else if(siOfSpousePA==22){
			insvalSpouse=700000; 
		}
		else if(siOfSpousePA==23){
			insvalSpouse=800000; 
		}
		else if(siOfSpousePA==24){
			insvalSpouse=900000; 
		}
		else if(siOfSpousePA==25){
			insvalSpouse=1000000; 
		}
		else if(siOfSpousePA==26){
			insvalSpouse=1100000; 
		}
		else if(siOfSpousePA==27){
			insvalSpouse=1200000; 
		}
		else if(siOfSpousePA==28){
			insvalSpouse=1300000; 
		}
		else if(siOfSpousePA==29){
			insvalSpouse=1400000; 
		}
		else if(siOfSpousePA==30){
			insvalSpouse=1500000; 
		}
		else if(siOfSpousePA==31){
			insvalSpouse=1600000; 
		}
		else if(siOfSpousePA==32){
			insvalSpouse=1700000; 
		}
		else if(siOfSpousePA==33){
			insvalSpouse=1800000; 
		}
		else if(siOfSpousePA==34){
			insvalSpouse=1900000; 
		}
		else if(siOfSpousePA==35){
			insvalSpouse=2000000; 
		}			
			if(insvalSpouse>insval){
			alert("PA SumInsured of Spouse can be upto 100% Self only ("+insval+")");
			oPage.getControl("PA_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue('');	
			return false;
			}	
	var tabObj2 = oPage.getControl("PA_Benefit_Table");   
	var row = tabObj2.getTableRows();	
	if(insvalSpouse !=null && insvalSpouse !='')
	{
		for (var i = 0; i < row.length; i++)
			{
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Accidental death")
				{								
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',insvalSpouse);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',insvalSpouse);
				
				}
				if(nam == "PTD")
				{				
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',insvalSpouse);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',insvalSpouse);				
				}
				if(nam == "Funeral Expenses")
				{
				var funSI=insvalSpouse*0.01;				
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_AD_GSA',insvalSpouse);				
				row[i].setValue('PolicyCtAcceBVO.InterestSi',funSI);				
				}
				
			}
		}
		
	
	
		
	
}
}
}
}


function setSumInsuredHDC(str,obj){
	var ctId = document.getElementById("_ctId").value; 
if (ctId =="900009486"){
	var  siOfCover_hdc = oPage.getControl("HDC_Benefit_Field_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].getValue();		
		var insval=0;
	
		if(siOfCover_hdc==1){
			insval=500;
		}
		else if(siOfCover_hdc==2){
			insval=1000;
		}
		else if(siOfCover_hdc==3){
			insval=1500;
		}
		else if(siOfCover_hdc==4){
			insval=2000;
		}
		else if(siOfCover_hdc==5){
			insval=2500;
		}
		else if(siOfCover_hdc==6){
			insval=3000; 
		}
		else if(siOfCover_hdc==7){
			insval=3500; 
		}
		else if(siOfCover_hdc==8){
			insval=4000; 
		}
		else if(siOfCover_hdc==9){
			insval=4500; 
		}
		else if(siOfCover_hdc==10){
			insval=5000; 
		}
		
var hdcMMinsi = "<%=hdcMinSI%>";
var hdcMMinsi1=parseInt(hdcMMinsi);	
var hdcMMaxsi = "<%=hdcMaxSI%>";
var hdcMMaxsi1=parseInt(hdcMMaxsi);	
var baseCoverSi1=parseInt(insval);
if(baseCoverSi1 < hdcMMinsi1 || baseCoverSi1 > hdcMMaxsi1){
	alert("SI should be within the range (Minimum "+hdcMMinsi1+" Maximum "+hdcMMaxsi1+") as defined in the Master Policy");
	oPage.getControl("HDC_Benefit_Field_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue('');	
	return false;
}

else{
	var tabObj2 = oPage.getControl("HDC_Benefit_Datatable_Details");   
	var row = tabObj2.getTableRows();	
	var hdcdura = "<%=hdcdur%>";
	if(baseCoverSi1 !=null && baseCoverSi1 !='')
	{
		for (var i = 0; i < row.length; i++)
			{
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Sickness Hospital daily cash")
				{
				var totalhdcsi=baseCoverSi1*hdcdura;
				row[i].setValue('PolicyCtAcceBVO.Sum_Insured_per_day_GSA',baseCoverSi1);
				row[i].setValue('PolicyCtAcceBVO.No_of_days_GSA',hdcdura);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',totalhdcsi);
				row[i].setValue('PolicyCtAcceBVO.Balance_No_of_Days',hdcdura);
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_GSA',totalhdcsi);
				}
				if(nam == "Accident Hospital daily cash")
				{
				var accSI=baseCoverSi1*2;
				var totalaccsi=accSI*hdcdura;
				row[i].setValue('PolicyCtAcceBVO.Sum_Insured_per_day_GSA',accSI);
				row[i].setValue('PolicyCtAcceBVO.No_of_days_GSA',hdcdura);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',totalaccsi);
				row[i].setValue('PolicyCtAcceBVO.Balance_No_of_Days',hdcdura);
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_GSA',totalaccsi);
				}
				if(nam == "ICU Cash Benefit")
				{
				var icuSI=baseCoverSi1*3;
				var totalicusi=icuSI*hdcdura;
				row[i].setValue('PolicyCtAcceBVO.Sum_Insured_per_day_GSA',icuSI);
				row[i].setValue('PolicyCtAcceBVO.No_of_days_GSA',hdcdura);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',totalicusi);
				row[i].setValue('PolicyCtAcceBVO.Balance_No_of_Days',hdcdura);
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_GSA',totalicusi);
				}
				if(nam == "Convalescence Benefit")
				{
				var convaSI=baseCoverSi1*5;                   
				row[i].setValue('PolicyCtAcceBVO.Sum_Insured_per_day_GSA',0);
				row[i].setValue('PolicyCtAcceBVO.No_of_days_GSA',0);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',convaSI);
				row[i].setValue('PolicyCtAcceBVO.Balance_No_of_Days',0);
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_GSA',0);
				}
				if(nam == "Compassionate Benefit")
				{
				var compSI=baseCoverSi1*10;
				row[i].setValue('PolicyCtAcceBVO.Sum_Insured_per_day_GSA',0);
				row[i].setValue('PolicyCtAcceBVO.No_of_days_GSA',0);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',compSI);
				row[i].setValue('PolicyCtAcceBVO.Balance_No_of_Days',0);
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_GSA',0);
				}
				if(nam == "Day Care Treatment Benefit")
				{
				var dayTRSI=baseCoverSi1*5;
				if(dayTRSI>10000){
					dayTRSI=10000;
				}
				row[i].setValue('PolicyCtAcceBVO.Sum_Insured_per_day_GSA',0);
				row[i].setValue('PolicyCtAcceBVO.No_of_days_GSA',0);
				row[i].setValue('PolicyCtAcceBVO.InterestSi',dayTRSI);
				row[i].setValue('PolicyCtAcceBVO.Balance_No_of_Days',0);
				row[i].setValue('PolicyCtAcceBVO.Balance_Sum_Insured_GSA',0);
				}
			}
		}
	}
	
	 setSumInsuredHDCCT();
	}
	
}



function setSumInsuredCI(str,obj){
	var ctId = document.getElementById("_ctId").value; 
if (ctId =="900009484"){
	var  siOfCover_CI = oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_Critical_Illness_GSA"].getValue();
		
		var insval=0;
	
		if(siOfCover_CI==1){
			insval=100000;
		}
		else if(siOfCover_CI==2){
			insval=200000;
		}
		else if(siOfCover_CI==3){
			insval=300000;
		}
		else if(siOfCover_CI==4){
			insval=400000;
		}
		else if(siOfCover_CI==5){
			insval=500000;
		}
		else if(siOfCover_CI==6){
			insval=600000; 
		}
		else if(siOfCover_CI==7){
			insval=700000; 
		}
		else if(siOfCover_CI==8){
			insval=800000; 
		}
		else if(siOfCover_CI==9){
			insval=900000; 
		}
		else if(siOfCover_CI==10){
			insval=1000000; 
		}
		
	 setDefaultSI_CI(insval, siOfCover_CI);
	 
	}
}

function setDefaultSI_CI(insval, drpValue)
{ 
	oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_Critical_Illness_GSA"].setValue(drpValue);
	oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue(insval);
	oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setReadOnly(true);	
	checkSIrange_CI();
	
}

function checkSIrange_CI(str,obj){
	
var ciMMinsi = "<%=ciMinSI%>";
var ciMMinsi1=parseInt(ciMMinsi);	
var ciMMaxsi = "<%=ciMaxSI%>";
var ciMMaxsi1=parseInt(ciMMaxsi);	
var  baseCoverSi = oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].getValue();
var baseCoverSi1=parseInt(baseCoverSi);
if(baseCoverSi1 < ciMMinsi1 || baseCoverSi1 > ciMMaxsi1){
	alert("SI should be within the range (Minimum "+ciMMinsi1+" Maximum "+ciMMaxsi1+") as defined in the Master Policy");
	oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_Critical_Illness_GSA"].setValue('');
	oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue('');
	oPage.getControl("CI_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setReadOnly(true);	
	return false;
}
}

function setSumInsured(str,obj){
	var ctId = document.getElementById("_ctId").value; 
if (ctId =="900009485"){
	var  siOfCover_CKP = oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].getValue();
		
		var insval=0;
	
		if(siOfCover_CKP==1){
			insval=50000;
		}
		else if(siOfCover_CKP==2){
			insval=100000;
		}
		else if(siOfCover_CKP==3){
			insval=150000;
		}
		else if(siOfCover_CKP==4){
			insval=200000;
		}
		else if(siOfCover_CKP==5){
			insval=250000;
		}
		else if(siOfCover_CKP==6){
			insval=300000; 
		}
		else if(siOfCover_CKP==7){
			insval=350000; 
		}
		else if(siOfCover_CKP==8){
			insval=400000; 
		}
		else if(siOfCover_CKP==9){
			insval=450000; 
		}
		else if(siOfCover_CKP==10){
			insval=500000; 
		}
		else if(siOfCover_CKP==11){
			insval=550000; 
		}
		else if(siOfCover_CKP==12){
			insval=600000; 
		}
		else if(siOfCover_CKP==13){
			insval=650000; 
		}
		else if(siOfCover_CKP==14){
			insval=700000; 
		}
		else if(siOfCover_CKP==15){
			insval=750000; 
		}
		else if(siOfCover_CKP==16){
			insval=800000; 
		}
		else if(siOfCover_CKP==17){
			insval=850000; 
		}
		else if(siOfCover_CKP==18){
			insval=900000; 
		}
		else if(siOfCover_CKP==19){
			insval=950000; 
		}
		else if(siOfCover_CKP==20){
			insval=1000000; 
		}
		
		var typeOfProduct = "<%=typeOfProduct%>";
		var famCategory = "<%=famCategory%>";
		if(typeOfProduct ==3 && famCategory >=8 && insval<600000){			
			alert("Sum insured for Floater combinations of 4A and higher combinations - start from 6 lacs");
			oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue('');
			oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue('');
			oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setReadOnly(true);	


			return false;			
		}
		else{
			setDefaultSI(insval, siOfCover_CKP);
		}
		
	 
	}
}

function setDefaultSI(insval, drpValue)
{ 
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue(drpValue);
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue(insval);
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setReadOnly(true);	
	checkSIrange();
	
}

function checkSIrange(str,obj){
	
var hospMMinsi = "<%=hospMinSI%>";
var hospMMinsi1=parseInt(hospMMinsi);	
var hospMMaxsi = "<%=hospMaxSI%>";
var hospMMaxsi1=parseInt(hospMMaxsi);	
var  baseCoverSi = oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].getValue();
var baseCoverSi1=parseInt(baseCoverSi);
if(baseCoverSi1 < hospMMinsi1 || baseCoverSi1 > hospMMaxsi1){
	alert("SI should be within the range (Minimum "+hospMMinsi+" Maximum "+hospMMaxsi+") defined in the Master Policy");
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue('');
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue('');
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setReadOnly(true);	
	return false;
}
else{
	setHospBenSI();
	return true;
}
}

function setHospBenOpt(str, obj)
{ 
var ctId = document.getElementById("_ctId").value; 
if (ctId =="900009485"){
	var  baseCoverSi = oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].getValue();
	var tabObj2 = oPage.getControl("HC_Benefit_Datatable");   
		var row = tabObj2.getTableRows();
	for (var i = 0; i < row.length; i++)
			{
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Hospitalization Medical Expenses")
				{
				row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',1);
				}
				else if(nam == "Maternity Expenses cover")
				{
					var isMatopted = "<%=isMaternity%>";					
					var matben =2;
					if(baseCoverSi == 0){
					if(isMatopted == 'Yes'){
						 matben = 1;					
					}
					}
					else if(baseCoverSi > 550000){
					if(isMatopted == 'Yes'){
						 matben = 1;					
					}
					}					
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',matben);
				}
				else if(nam == "New born expense cover")
				{					
				var isNbornopted = "<%=isNewborn%>";
					var newbornben =2;
					if(baseCoverSi == 0 && matben == 1){
					if(isNbornopted == 'Yes'){
						 newbornben = 1;					
					}	
					}
					else if(baseCoverSi > 550000 && matben == 1){
					if(isNbornopted == 'Yes'){
						 newbornben = 1;					
					}	
					}					
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',newbornben);
				}
				else if(nam == "Outpatient expense cover")
				{
					
					var isoutPopted = "<%=isOutpat%>";
					var outPben =2;
					if(baseCoverSi ==0){
					if(isoutPopted == 'Yes'){
						 outPben = 1;					
					}
					}
					else if(baseCoverSi > 550000){
					if(isoutPopted == 'Yes'){
						 outPben = 1;					
					}
					}				
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',outPben);
				}
				else if(nam == "Co-payment")
				{					
				 var isCopayopt = "<%=isCopayopt%>";
					var copayben =2;
					var coPay=0;
					if(isCopayopt == 'Yes'){
						 copayben = 1;	
						coPay =20;						 
					}					
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',copayben);		
					oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Percentage_of_copay_GSA"].setValue(coPay);
			
				}
				else if(nam == "Aggregate deductible")
				{					
				 var isAgreeopt = "<%=isAggregate%>";
					var agreeben =2;
					if(baseCoverSi == 0){					
					if(isAgreeopt == 'Yes'){
						 agreeben = 1;												 
					}
					}
					else if(baseCoverSi > 550000){					
					if(isAgreeopt == 'Yes'){
						 agreeben = 1;												 
					}
					}					
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',agreeben);
				}				
			}
}
}

function setHospBenSI(str, obj){ 

var relationProp= '<%=request.getParameter("RelationshipWithProposerASAN")%>';
var ctId = document.getElementById("_ctId").value; 
if (ctId =="900009485"){
		
		var  baseCoverSi = oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].getValue();
		var tabObj2 = oPage.getControl("HC_Benefit_Datatable");   
		var row = tabObj2.getTableRows();
		var copayopt =2;
		var outopt=2;
		var matopt=2;
		var newopt=2;
	if(baseCoverSi !=null && baseCoverSi !='')
	{	
	
		for (var i = 0; i < row.length; i++)
			{
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Hospitalization Medical Expenses")
				{
				row[i].setValue('PolicyCtAcceBVO.InterestSi',baseCoverSi);
				}
				else if(nam == "Maternity Expenses cover")
				{
					
					var matSIper =0;
					var matopt = "<%=isMaternity%>";
					if(baseCoverSi >550000){
					
					if(matopt == 'Yes'){
						var matSIper = baseCoverSi*0.1;
					if(matSIper>100000){
						matSIper=100000;
					}
					row[i].setValue('PolicyCtAcceBVO.InterestSi',matSIper);
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',1);
					}
					}
					else{
					row[i].setValue('PolicyCtAcceBVO.InterestSi',0);
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',2);
					}
					
				}
				else if(nam == "New born expense cover")
				{
					
					var newSIper =0;
					var matopt = "<%=isMaternity%>";								
					var newopt = "<%=isNewborn%>";	
					if(baseCoverSi >550000 && matopt == 'Yes'){
					if(newopt == 'Yes'){
					var newSIper = baseCoverSi*0.2;
					if(newSIper>200000){
						newSIper=200000;
					}
					row[i].setValue('PolicyCtAcceBVO.InterestSi',newSIper);
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',1);
					}
					}				
				else{
					row[i].setValue('PolicyCtAcceBVO.InterestSi',0);
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',2);
					}
				}
				else if(nam == "Outpatient expense cover")
				{
					
					var outSIper =0;
					var outopt = "<%=isOutpat%>";					
					if(baseCoverSi >550000){					
					if(outopt == 'Yes'){
					var outSIper = baseCoverSi*0.01;
					if(outSIper>10000){
						outSIper=10000;
					}
					row[i].setValue('PolicyCtAcceBVO.InterestSi',outSIper);
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',1);
					}
					}
					else{
					row[i].setValue('PolicyCtAcceBVO.InterestSi',0);
					row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',2);
					}					
				
				}
				else if(nam == "Aggregate deductible")
				{
					 var isAgreeopt = "<%=isAggregate%>";					 
					if(baseCoverSi <600000){
						row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',2);
						oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setValue('');
						oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setReadOnly(true);
						oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setRequired(false);

					}
					else{
						if(isAgreeopt == 'Yes'){
							row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',1);
							oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setReadOnly(false);
							oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setRequired(true);

						}
						else{
							row[i].setValue('PolicyCtAcceBVO.Benefit_Opted_GSA',2);
							oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setValue('');
							oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setReadOnly(true);
							oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setRequired(false);

						}	
					}
				}
				else if(nam == "Co-payment")
				{					
				 copayopt = row[i].getValue("PolicyCtAcceBVO.Benefit_Opted_GSA");	
				}				
			}
			var roomCrg = baseCoverSi*(0.02);
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Room_Boarding_Charges_GSA"].setValue(roomCrg);
			var icuCrg = baseCoverSi*(0.04);
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.ICU_Charges_GSA"].setValue(icuCrg);
				var prehosp = 30;
				var posthsop = 60;
				if(baseCoverSi>550000)
				{
					prehosp=60;
					posthsop=90;
				}			
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Pre_Hospitalisation_Days_GSA"].setValue(prehosp);
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Post_Hospitalisation_Days_GSA"].setValue(posthsop);
			var mentSIper = baseCoverSi*0.1;
					if(mentSIper>50000){
						mentSIper=50000;
					}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Mental_Illness_SI"].setValue(mentSIper);
			var maxAmb = 5000;
			if(baseCoverSi>550000){
				maxAmb = 10000;
			}
			var maxdom = 50000
			if(baseCoverSi>550000){
				maxdom = 200000;
			}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Max_Domicilary_Expenses_GSA"].setValue(maxdom);
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Max_Ambulance_charges_GSA"].setValue(maxAmb);
			var ampSIper = baseCoverSi*0.01;
					if(ampSIper>maxAmb){
						ampSIper=maxAmb;
					}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Ambulance_charges_GSA"].setValue(ampSIper);
			var domSIper = baseCoverSi*0.2;
					if(domSIper>maxdom){
						domSIper=maxdom;
					}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Domicilary_Hospitalization_GSA"].setValue(domSIper);
			var reinSIper =0;
			if(baseCoverSi>=600000){
				 reinSIper =100;				
			}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Reinstatement_SI_GSA"].setValue(reinSIper);
			var reinSI =0;
			if(baseCoverSi>=600000){
				 reinSI =baseCoverSi;				
			}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Reinstatement_SI"].setValue(reinSI);
			var organSI =0;
			if(baseCoverSi>=600000){
				 organSI =baseCoverSi*0.5;				
			}
			
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Organ_Donor_expense_GSA"].setValue(organSI);
			var coPay =0;			
			if(copayopt == 1){
			 coPay =20;
			}
			oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Percentage_of_copay_GSA"].setValue(coPay);
			
			return true;
		
		}
	}
	return true;
} 

function setdeduct(str, obj)
{ 

		var isAgreeopt = "<%=isAggregate%>";
		var  baseCoverSi = oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].getValue();
					if(isAgreeopt == 'Yes' && baseCoverSi>550000){
					oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setReadOnly(false);
												oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setRequired(true);

					}	
else{
		oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setValue('');
		oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setReadOnly(true);
		oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setRequired(false);

}	
}
function updateGSASI()
{
	
	var pol_id = <%=policy_Id%>;
var whereclause = 'policy_id='+pol_id+'';
var count = getData("T_INSURED_LIST", "COUNT(INSURED_ID)", whereclause );
if(count > '1')
{
var productType = <%=typeOfProduct%>;
if (productType == '3' )
{
if(relationshipPrposer != '1')
{

     var sql1 =  'FIELD17 in (select insured_id from t_insured_list where policy_id = ' + pol_id + ' and liability_status In (1,2)) and field1=1';
     var insId = getData("T_GS_RESERVE_TB_900000800", "FIELD17", sql1);			
	if (insId != null && insId != ''){
	var sql2 = "liability_status In (1,2) and ct_id = '900009485' and insured_id = '"+insId+"'";
	var siOfCover_CKP = getData("T_POLICY_CT", "field10", sql2);
	var policy_ct_id = getData("T_POLICY_CT", "policy_ct_id", sql2);
	var sql3 = "interest_id = 900006612 and policy_ct_id = '"+policy_ct_id+"'";
	var deductible = getData("T_POLICY_CT_ACCE","field30",sql3);
	var insval=0;	
		if(siOfCover_CKP==1){
			insval=50000;
		}
		else if(siOfCover_CKP==2){
			insval=100000;
		}
		else if(siOfCover_CKP==3){
			insval=150000;
		}
		else if(siOfCover_CKP==4){
			insval=200000;
		}
		else if(siOfCover_CKP==5){
			insval=250000;
		}
		else if(siOfCover_CKP==6){
			insval=300000; 
		}
		else if(siOfCover_CKP==7){
			insval=350000; 
		}
		else if(siOfCover_CKP==8){
			insval=400000; 
		}
		else if(siOfCover_CKP==9){
			insval=450000; 
		}
		else if(siOfCover_CKP==10){
			insval=500000; 
		}
		else if(siOfCover_CKP==11){
			insval=550000; 
		}
		else if(siOfCover_CKP==12){
			insval=600000; 
		}
		else if(siOfCover_CKP==13){
			insval=650000; 
		}
		else if(siOfCover_CKP==14){
			insval=700000; 
		}
		else if(siOfCover_CKP==15){
			insval=750000; 
		}
		else if(siOfCover_CKP==16){
			insval=800000; 
		}
		else if(siOfCover_CKP==17){
			insval=850000; 
		}
		else if(siOfCover_CKP==18){
			insval=900000; 
		}
		else if(siOfCover_CKP==19){
			insval=950000; 
		}
		else if(siOfCover_CKP==20){
			insval=1000000; 
		}	
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setValue(siOfCover_CKP);	
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue(insval);
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.Sum_Insured_CKP"].setReadOnly(true);	
	oPage.getControl("HC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setReadOnly(true);	
	setHospBenSI();
	if (deductible != null && deductible != ''){
	oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setValue(deductible);
	oPage.getControl("HC_Benefit_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtAcceBOList[0].PolicyCtAcceBVO.Deductible_Amount_GSA"].setReadOnly(true);
	}
}
}
}
}
}

function setSumInsuredPACT(str,obj){
	var ctId = document.getElementById("_ctId").value; 
	if (ctId =="900009643"){
		var tabObj2 = oPage.getControl("PA_Benefit_Table");   
		var row = tabObj2.getTableRows();
var Adsi=0;
var FEsi=0;
var SIInt=0;		
		for (var i = 0; i < row.length; i++)
			{
				
				var nam = row[i].getValue("PolicyCtAcceBVO.InterestName");
				
				if(nam == "Accidental death")
				{								
				 Adsi = row[i].getValue("PolicyCtAcceBVO.InterestSi");
				
				}
				if(nam == "Funeral Expenses")
				{
				 FEsi = row[i].getValue("PolicyCtAcceBVO.InterestSi");			
				}								 
				 SIInt = parseInt(Adsi)+parseInt(FEsi);
				}
			oPage.getControl("PA_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setVisible(true);					
			oPage.getControl("PA_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue(SIInt);
			oPage.getControl("PA_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setVisible(false);	
			
	}
}
function setSumInsuredHDCCT(str,obj){
	var ctId = document.getElementById("_ctId").value; 
	if (ctId =="900009486"){
		var tabObj2 = oPage.getControl("HDC_Benefit_Datatable_Details");   
		var row = tabObj2.getTableRows();
var si=0;
var SIInt=0;		
		for (var i = 0; i < row.length; i++)
			{
				 si = row[i].getValue("PolicyCtAcceBVO.InterestSi");				 
				 SIInt = parseInt(si)+parseInt(SIInt);
				}
			oPage.getControl("HDC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setVisible(true);				
			oPage.getControl("HDC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setValue(SIInt);
			oPage.getControl("HDC_Cover_Details").getFormEditors()["PolicyCtBO.PolicyCtBO.PolicyCtBVO.SiOfCoverType"].setVisible(false);	
			
	}
}


window.attachEvent("onload", function(){	
	var RelationshipWithProposerASAN= '<%=request.getParameter("RelationshipWithProposerASAN")%>';
	relationshipPrposer = RelationshipWithProposerASAN;		
	var ctId = document.getElementById("_ctId").value;
	if (ctId =="900009485"){
		updateGSASI();		
		setHospBenOpt();
		if(relationshipPrposer == '1'){
			setdeduct();
		}				
	}
	
	if (ctId =="900009643"){
		updatePA_SI();
	}	
});

</script>

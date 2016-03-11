package com.lnpdit.mobilealarm.webservice;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import com.lnpdit.mobilealarm.entity.LoginUser;
import com.lnpdit.mobilealarm.entity.TagInfo;
import com.lnpdit.mobilealarm.utils.EventCache;
import com.lnpdit.mobilealarm.utils.SOAP_UTILS;
import com.lnpdit.mobilealarm.webservice.AsyncTaskBase.SoapObjectResult;

public class SoapService implements ISoapService {
	private AsyncTaskBase asynTaskBase = new AsyncTaskBase();
	private SoapRes soapRes = new SoapRes();

	@Override
	public void CheckUserLogin(Object[] property_va) {
		String[] property_nm = { "mobileNo", "passWd" };
		asynTaskBase.setMethod(SOAP_UTILS.METHOD.CHECKUSERLOGIN);
		asynTaskBase.setProperty_nm(property_nm);
		asynTaskBase.setProperty_va(property_va);
		asynTaskBase.executeDo(new SoapObjectResult() {

            @Override
            public void soapResult(SoapObject obj) {
                LoginUser loginUser = null;
                SoapObject soapchild = (SoapObject) obj.getProperty(0);
                SoapObject soapchilds = (SoapObject) soapchild.getProperty(1);
                SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
                SoapObject soapchildsss = (SoapObject) soapchildss.getProperty(0);
                    loginUser = new LoginUser();
                    loginUser.setId(soapchildsss.getProperty("Id").toString());
                    loginUser.setMobileNo(soapchildsss.getProperty("mobileNo")
                            .toString());
                    loginUser.setPassWd(soapchildsss.getProperty("passWd").toString());
                    loginUser.setSex(soapchildsss.getProperty("sex")
                            .toString());
                    loginUser.setHdPhoto(soapchildsss.getProperty(
                            "hdPhoto").toString());
                    loginUser.setIslock(soapchildsss.getProperty("islock")
                            .toString());
                    loginUser.setCrTime(soapchildsss.getProperty("crTime")
                            .toString());
                
                soapRes.setObj(loginUser);
                soapRes.setCode(SOAP_UTILS.METHOD.CHECKUSERLOGIN);
                EventCache.commandActivity.post(soapRes);
            }

            @Override
            public void soapError() {
                soapRes.setObj(null);
                soapRes.setCode(SOAP_UTILS.METHOD.CHECKUSERLOGIN);
                EventCache.commandActivity.post(soapRes);
            }
        });
    }

	   @Override
	    public void AddUsers(Object[] property_va) {
	        String[] property_nm = { "mobileNo", "passWd" ,"sex","code"};
	        asynTaskBase.setMethod(SOAP_UTILS.METHOD.ADDUSERS);
	        asynTaskBase.setProperty_nm(property_nm);
	        asynTaskBase.setProperty_va(property_va);
	        asynTaskBase.executeDo(new SoapObjectResult() {

                @Override
                public void soapResult(SoapObject obj) {
                    soapRes.setObj(obj.getProperty("AddUsersResult"));
                    System.out.println(">>>>CODE  :　" + obj.toString());
                    soapRes.setCode(SOAP_UTILS.METHOD.ADDUSERS);
                    EventCache.commandActivity.post(soapRes);
                }

                @Override
                public void soapError() {
                    soapRes.setObj(null);
                    soapRes.setCode(SOAP_UTILS.METHOD.ADDUSERS);
                    EventCache.commandActivity.post(soapRes);
                }
            });

        }
	   @Override
	   public void SetIdentifyCode(Object[] property_va) {
	       String[] property_nm = { "mobileNo"};
	       asynTaskBase.setMethod(SOAP_UTILS.METHOD.SETIDENTIFYCODE);
	       asynTaskBase.setProperty_nm(property_nm);
	       asynTaskBase.setProperty_va(property_va);
	        asynTaskBase.executeDo(new SoapObjectResult() {

	            @Override
	            public void soapResult(SoapObject obj) {
	                LoginUser loginUser = null;
	                SoapObject soapchild = (SoapObject) obj.getProperty(0);
	                SoapObject soapchilds = (SoapObject) soapchild.getProperty(1);
	                SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
	                SoapObject soapchildsss = (SoapObject) soapchildss.getProperty(0);
	                    loginUser = new LoginUser();
	                    loginUser.setId(soapchildsss.getProperty("Id").toString());
	                    loginUser.setMobileNo(soapchildsss.getProperty("mobileNo")
	                            .toString());
	                    loginUser.setPassWd(soapchildsss.getProperty("passWd").toString());
	                    loginUser.setSex(soapchildsss.getProperty("sex")
	                            .toString());
	                    loginUser.setHdPhoto(soapchildsss.getProperty(
	                            "hdPhoto").toString());
	                    loginUser.setIslock(soapchildsss.getProperty("islock")
	                            .toString());
	                    loginUser.setCrTime(soapchildsss.getProperty("crTime")
	                            .toString());
	                
	                soapRes.setObj(loginUser);
	                soapRes.setCode(SOAP_UTILS.METHOD.SETIDENTIFYCODE);
	                EventCache.commandActivity.post(soapRes);
	            }

	            @Override
	            public void soapError() {
	                soapRes.setObj(null);
	                soapRes.setCode(SOAP_UTILS.METHOD.SETIDENTIFYCODE);
	                EventCache.commandActivity.post(soapRes);
	            }
	        });
	    }
	   @Override
       public void updateByCode(Object[] property_va) {
           String[] property_nm = { "mobileNo","passWd","code"};
           asynTaskBase.setMethod(SOAP_UTILS.METHOD.UPDATEBYCODE);
           asynTaskBase.setProperty_nm(property_nm);
           asynTaskBase.setProperty_va(property_va);
            asynTaskBase.executeDo(new SoapObjectResult() {

                @Override
                public void soapResult(SoapObject obj) {
                    LoginUser loginUser = null;
                    SoapObject soapchild = (SoapObject) obj.getProperty(0);
                    SoapObject soapchilds = (SoapObject) soapchild.getProperty(1);
                    SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0);
                    SoapObject soapchildsss = (SoapObject) soapchildss.getProperty(0);
                        loginUser = new LoginUser();
                        loginUser.setId(soapchildsss.getProperty("Id").toString());
                        loginUser.setMobileNo(soapchildsss.getProperty("mobileNo")
                                .toString());
                        loginUser.setPassWd(soapchildsss.getProperty("passWd").toString());
                        loginUser.setSex(soapchildsss.getProperty("sex")
                                .toString());
                        loginUser.setHdPhoto(soapchildsss.getProperty(
                                "hdPhoto").toString());
                        loginUser.setIslock(soapchildsss.getProperty("islock")
                                .toString());
                        loginUser.setCrTime(soapchildsss.getProperty("crTime")
                                .toString());
                    
                    soapRes.setObj(loginUser);
                    soapRes.setCode(SOAP_UTILS.METHOD.UPDATEBYCODE);
                    EventCache.commandActivity.post(soapRes);
                }

                @Override
                public void soapError() {
                    soapRes.setObj(null);
                    soapRes.setCode(SOAP_UTILS.METHOD.UPDATEBYCODE);
                    EventCache.commandActivity.post(soapRes);
                }
            });
        }
	   @Override
       public void  SetIdentifyCode_ResetPWD(Object[] property_va) {
           String[] property_nm = { "mobileNo"};
           asynTaskBase.setMethod(SOAP_UTILS.METHOD.SETIDENTIFYCODE_RESETPWD);
           asynTaskBase.setProperty_nm(property_nm);
           asynTaskBase.setProperty_va(property_va);
           asynTaskBase.executeDo(new SoapObjectResult() {

               @Override
               public void soapResult(SoapObject obj) {
                   soapRes.setObj(obj.getProperty("SetIdentifyCode_ResetPWDResult"));
                   System.out.println(">>>>CODE  :　" + obj.toString());
                   soapRes.setCode(SOAP_UTILS.METHOD.SETIDENTIFYCODE_RESETPWD);
                   EventCache.commandActivity.post(soapRes);
               }

               @Override
               public void soapError() {
                   soapRes.setObj(null);
                   soapRes.setCode(SOAP_UTILS.METHOD.SETIDENTIFYCODE_RESETPWD);
                   EventCache.commandActivity.post(soapRes);
               }
           });

       }
	   @Override
	   public void  updateUserPWD(Object[] property_va) {
	       String[] property_nm = { "mobileNo","passWdO","passWdN"};
	       asynTaskBase.setMethod(SOAP_UTILS.METHOD.UPDATEUSERPWD);
	       asynTaskBase.setProperty_nm(property_nm);
	       asynTaskBase.setProperty_va(property_va);
	       asynTaskBase.executeDo(new SoapObjectResult() {

               @Override
               public void soapResult(SoapObject obj) {
                   soapRes.setObj(obj.getProperty("updateUserPWDResult"));
                   System.out.println(">>>>CODE  :　" + obj.toString());
                   soapRes.setCode(SOAP_UTILS.METHOD.UPDATEUSERPWD);
                   EventCache.commandActivity.post(soapRes);
               }

               @Override
               public void soapError() {
                   soapRes.setObj(null);
                   soapRes.setCode(SOAP_UTILS.METHOD.UPDATEUSERPWD);
                   EventCache.commandActivity.post(soapRes);
               }
           });

       }
	   @Override
       public void  transforDate(Object[] property_va) {
           String[] property_nm = { "MobileNumber","TagName","CoordinateX","CoordinateY","Adress","VideoName",
                   "AudioName","TextContents","Pic1","Pic2","Pic3","Pic4","Pic5","Pic6","cTime"};
           asynTaskBase.setMethod(SOAP_UTILS.METHOD.TRANSFORDATE);
           asynTaskBase.setProperty_nm(property_nm);
           asynTaskBase.setProperty_va(property_va);
           asynTaskBase.executeDo(new SoapObjectResult() {

               @Override
               public void soapResult(SoapObject obj) {
                   soapRes.setObj(obj.getProperty("transforDateResult"));
                   System.out.println(">>>>CODE  :　" + obj.toString());
                   soapRes.setCode(SOAP_UTILS.METHOD.TRANSFORDATE);
                   EventCache.commandActivity.post(soapRes);
               }

               @Override
               public void soapError() {
                   soapRes.setObj(null);
                   soapRes.setCode(SOAP_UTILS.METHOD.TRANSFORDATE);
                   EventCache.commandActivity.post(soapRes);
               }
           });

       }
	   
	   @Override
       public void  SaveFile(Object[] property_va) {
           String[] property_nm = {};
           asynTaskBase.setMethod(SOAP_UTILS.METHOD.SAVEFILE);
           asynTaskBase.setProperty_nm(property_nm);
           asynTaskBase.setProperty_va(property_va);
           asynTaskBase.executeDo(new SoapObjectResult() {

               @Override
               public void soapResult(SoapObject obj) {
                   soapRes.setObj(obj.getProperty("SaveFileResult"));
                   System.out.println(">>>>CODE  :　" + obj.toString());
                   soapRes.setCode(SOAP_UTILS.METHOD.SAVEFILE);
                   EventCache.commandActivity.post(soapRes);
               }

               @Override
               public void soapError() {
                   soapRes.setObj(null);
                   soapRes.setCode(SOAP_UTILS.METHOD.SAVEFILE);
                   EventCache.commandActivity.post(soapRes);
               }
           });

       }
	   
       @Override
       public void GetTagByDesc(Object[] property_va) {
           String[] property_nm = {};
           asynTaskBase.setMethod(SOAP_UTILS.METHOD.GETTAGBYDESC);
           asynTaskBase.setProperty_nm(property_nm);
           asynTaskBase.setProperty_va(property_va);
            asynTaskBase.executeDo(new SoapObjectResult() {

                @Override
                public void soapResult(SoapObject obj) {
                    
                    SoapObject soapchild = (SoapObject) obj.getProperty(0);
                    SoapObject soapchilds = (SoapObject) soapchild.getProperty(1);
                    SoapObject soapchildss = (SoapObject) soapchilds.getProperty(0); 
                    List<TagInfo> taglist = new ArrayList<TagInfo>();
                    for (int i = 0; i < soapchildss.getPropertyCount(); i++) {                        
                        SoapObject soapchildsss = (SoapObject) soapchildss.getProperty(i);
                        TagInfo tagInfo = new TagInfo();
                        tagInfo.setId(soapchildsss.getProperty("Id").toString());
                        tagInfo.setTagName(soapchildsss.getProperty("TagName")
                                .toString());
                        tagInfo.setTCount(soapchildsss.getProperty("TCount").toString());
                        taglist.add(tagInfo);
                    }
                    
                    soapRes.setObj(taglist);
                    soapRes.setCode(SOAP_UTILS.METHOD.GETTAGBYDESC);
                    EventCache.commandActivity.post(soapRes);
                }

                @Override
                public void soapError() {
                    soapRes.setObj(null);
                    soapRes.setCode(SOAP_UTILS.METHOD.GETTAGBYDESC);
                    EventCache.commandActivity.post(soapRes);
                }
            });
        }
}

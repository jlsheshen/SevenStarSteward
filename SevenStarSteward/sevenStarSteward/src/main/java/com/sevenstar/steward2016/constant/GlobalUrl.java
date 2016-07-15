package com.sevenstar.steward2016.constant;

public class GlobalUrl {
	//public static final String ADDR = "http://192.168.1.120:8080/web/";
	public static final String ADDR = "http://139.196.58.106/";
	// public static final String ADDR = "http://10.111.10.33:8080/web/";
	public static final String REGISTER_URL = ADDR + "appMethod/pho_signin.do";
	public static final String Retrieve_PASSWROD_URL = ADDR + "appMethod/pho_find_password.do";

	public static final String GET_PHONE_CODE_URL = ADDR + "appMethod/getCode.do";
	public static final String USER_LOGIN_URL = ADDR + "appMethod/pho_login.do";
	public static final String USER_SAVE_URL = ADDR + "appMethod/pho_save.do";
	// 首页服务
	public static final String GET_HOME_SERVICE = ADDR + "appService/getServiceList.do";
	// 生活服务
	public static final String GET_MAIN_LIVE_SERVICE = ADDR + "appService/getLiveServiceList.do";
	public static final String getLiveServiceData = ADDR + "appService/getLiveServiceData.do";
	// 乐购_租用信息
	public static final String GET_HAPPY_SHOP_RET_INFO_URL = ADDR + "appHappyShop/getOptionalEquipmentInfoList.do";
	// 乐购_众筹
	public static final String GET_HAPPY_SHOP_KICK_STARTER_URL = ADDR + "appHappyShop/getMarkInfoList.do";
	// 乐购_特色订购
	public static final String GET_HAPPY_SHOP_Feature_Order_URL = ADDR + "appHappyShop/getSpecialOrderInfoList.do";
	// 乐购_商品信息
	public static final String GET_HAPPY_SHOP_COMMODITY_URL = ADDR + "appHappyShop/getProductInfoList.do";

	// 广告
	public static final String GET_AppAdInfo_AD_LIST_URL = ADDR + "appAdInfo/getAdList.do";

	// 订单
	public static final String INSTALL_Live_ORDER_URL = ADDR + "appOrder/installLiveOrder.do";
	// 服务套餐
	public static final String INSTALL_Service_ORDER_URL = ADDR + "appOrder/installServiceMainOrder.do";
	// 乐购图片
	public static final String HAPPY_SHOP_BANNER_IMAGE_URL = ADDR + "appHappyShop/getShopContentImages.do";

	// 乐购订单
	public static final String HAPPY_SHOP_ORDER_URL = ADDR + "appOrder/installShopOrder.do";
	// 我的购物订单
	public static final String MY_SHOP_ORDER_URL = ADDR + "appOrder/getMyShopOrder.do";

	public static final String MY_SHOP_INFO_URL = ADDR + "appHappyShop/getMyShopInfo.do";
	public static final String MY_SERVICE_PACKAGE_ORDER_URL = ADDR + "appOrder/getMyServicePackage.do";

	public static final String MY_HOUR_SERVICE_ORDER_URL = ADDR + "appOrder/getMyHourServiceOrder.do";
	public static final String MY_ACTIVITY_ORDER_URL = ADDR + "appOrder/getMyActivityOrder.do";
	public static final String MY_TRAVEL_ORDER_URL = ADDR + "appOrder/getMyTravelOrder.do";
	public static final String MY_RELEASE_INFO_URL = ADDR + "appOther/getMyReleaseInfo.do";
	public static final String MY_BlACK_LIST_URL = ADDR + "appOther/getMyBlackList.do";
	public static final String MY_COLLECTION_LIST_URL = ADDR + "appOther/getCollectionList.do";
	public static final String SEND_MY_HEAD_IMAGE_URL = ADDR + "appUser/uploadFileFolder.do";
	// 我的社区列表数据
	public static final String GET_COMMUNITY_LIST_DATA = ADDR + "appCommunity/getCommunityListData.do";

	// 社区图片
	public static final String GET_COMMUNITY_BANNER_IMAGE_URL = ADDR + "appCommunity/getCommunityContentImages.do";
	// 节日套餐详情数据
	public static final String GET_HOLIDAY_CONTENT_DATA_URL = ADDR + "appService/getHolidayContent.do";

	// 我的评估
	public static final String MY_PING_GU_LIST_URL = ADDR + "appOther/getMyPingGuList.do";
	// 我的合同
	public static final String MY_HE_TONG_LIST_URL = ADDR + "appOther/getMyHeTongList.do";
	// 修改评估合同状态
	public static final String EDIT_PING_GU_HE_TONG_STATE = ADDR + "appOther/editPingGuOrHeTongState.do";
	public static final String getFitnessHomeData = ADDR + "appFitness/getFitnessHomeData.do";

	// 血缘关系列表
	public static final String getRelationsListDataData = ADDR + "appFitness/getRelationsListDataData.do";
	// 添加体检用户
	public static final String addHealthUser = ADDR + "appFitness/addHealthUser.do";
	// 添加体检用户
	public static final String removeHealthUser = ADDR + "appFitness/removeHealthUser.do";
	//根据体检用户获取最后一次体检数据
	public static final String getHealthRecordData = ADDR + "appFitness/getHealthRecordData.do";
	//获取支付信息接口
	public static final String getPayOrderInfo = ADDR + "appPay/getPayOrderInfo.do";
	public static final String installXiaoFeiOrder=ADDR +"appOrder/installXiaoFeiOrder.do";
	public static final String getAgentListInfo=ADDR +"appOther/getAgentListInfo.do";



}

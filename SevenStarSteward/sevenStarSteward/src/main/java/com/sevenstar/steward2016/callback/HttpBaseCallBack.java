package com.sevenstar.steward2016.callback;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.Callback;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.lib.shiguotao.adapter.ShiGuoTaoBaseAdapter;
import com.lib.shiguotao.callBack.GetDataCallBack;
import com.lib.shiguotao.callBack.HttpFailCallBack;
import com.lib.shiguotao.utils.LogUtils;
import com.lib.shiguotao.utils.OtherUtils;
import com.lib.shiguotao.utils.ViewUtils;
import com.lib.shiguotao.view.listview.CustomListView;
import com.lib.shiguotao.view.listview.CustomListViewHeader;
import com.sevenstar.steward2016.R;
import com.sevenstar.steward2016.manager.HttpSendMananger;
import com.sevenstar.steward2016.view.LoadDataErrorLayout;

public class HttpBaseCallBack<T> extends HttpFailCallBack implements GetDataCallBack, Callback.CommonCallback<String> {

	private Context context;
	private ShiGuoTaoBaseAdapter<T> adapter;
	private CustomListView mCustomListView;
	private int page = 1;
	private boolean isLoadNetData = true;
	private boolean isShowData = true;
	private List<T> cacheList = new ArrayList<T>();
	private List<T> showList = null;
	private boolean isRefresh = true;
	private long reFreshTime;
	private String url;
	private int totalPage;
	private Class<T> mClass;
	private int type;
	private int cacheSize = 10;
	private String[] paramsStr;

	/**
	 * 
	 * @param context
	 * @param showList
	 *            显示数据的ArrayList
	 * @param adapter
	 * @param mCustomListView
	 *            自定义的ListView
	 * @param loadingLayout
	 *            显示错误的布局
	 * @param mClass
	 *            Adapter中的实体对象类型 格式：Object.class
	 * @param footerTextRes
	 *            定义所有数据拉取完，ListView 底部显示文字
	 * @param url
	 *            请求接口
	 * @param type
	 *            用于 loadingLayout 没有数据时显示的图片
	 */
	public HttpBaseCallBack(Context context, List<T> showList, ShiGuoTaoBaseAdapter<T> adapter, CustomListView mCustomListView,
			LoadDataErrorLayout loadingLayout, Class<T> mClass, String url, String... paramsStr) {
		this(context, showList, adapter, mCustomListView, loadingLayout, mClass, url, 0, paramsStr);
	}

	public HttpBaseCallBack(Context context, List<T> showList, ShiGuoTaoBaseAdapter<T> adapter, CustomListView mCustomListView,
			LoadDataErrorLayout loadingLayout, Class<T> mClass, String url, int type, String... paramsStr) {
		this.context = context;
		this.adapter = adapter;
		this.mCustomListView = mCustomListView;
		ViewUtils.setVisibility(View.GONE, mCustomListView);
		this.loadingLayout = loadingLayout;
		this.mCustomListView.setGetDataCallBack(this);
		this.showList = showList;
		this.url = url;
		this.mClass = mClass;
		this.paramsStr = paramsStr;
		this.type = type;
		onLoadMore();
	}

	@Override
	public void loadData() throws Exception {
		onLoadMore();
	}

	@Override
	public View getDataShowLayout() {
		return mCustomListView;
	}

	@Override
	public boolean isHaveData() {
		return OtherUtils.isListNotEmpty(showList);
	}

	@Override
	public void onRefresh() {
		if (isRefresh) {
			isRefresh = false;
			if (isLoadNetData && System.currentTimeMillis() > reFreshTime + 300000) {
				reFreshTime = System.currentTimeMillis();
				HttpSendMananger.sendPost(url, 1, this, paramsStr);
			} else {
				setUpdateView(R.string.custom_listview_head_info_ok);
				isRefresh = true;
			}

		}
	}

	public void onRefreshData() {
		isRefresh = false;
		reFreshTime = System.currentTimeMillis();
		HttpSendMananger.sendPost(url, 1, this, paramsStr);
	}

	private void setUpdateView(int res) {
		CustomListViewHeader view = mCustomListView.getHeadLayout();
		view.finishView(context.getString(res));
		view.invalidate();
		mCustomListView.resetVisiableHeaderHeight();
	}

	public void searchData(String... strings) {
		paramsStr = strings;
		isShowData = true;
		isLoadNetData = true;
		page = 1;
		loadingLayout.setVisibility(View.VISIBLE);
		loadingLayout.showWaitLayout();
		if (showList != null && showList.size() > 0) {
			showList.clear();
			cacheList.clear();
		}
		onLoadMore();
	}

	@Override
	public void onLoadMore() {
		if (isShowData) {
			isShowData = false;
			int size = cacheList.size() > cacheSize ? cacheSize : cacheList.size();
			if (size > 0) {
				List<T> tmpList = new ArrayList<T>(size);
				for (int i = 0; i < size; i++) {
					tmpList.add(cacheList.remove(0));
				}
				showList.addAll(tmpList);
				adapter.setList(showList);
				adapter.notifyDataSetChanged();
				isShowData = true;
			} else {
				if (!isLoadNetData && cacheList.size() <= 0) {
					isShowData = false;
				} else {
					downData();
				}
			}
		}

	}

	@Override
	public void onFailure(String msg) {
		super.onFailure(msg);
		if (!isRefresh) {
			isRefresh = true;
			setUpdateView(R.string.custom_listview_head_info_error);
		} else {
			isShowData = true;
			isLoadNetData = true;
		}

	}

	@Override
	public void downData() {
		if (isLoadNetData && cacheList.size() < cacheSize * 3) {
			isLoadNetData = false;
			HttpSendMananger.sendPost(url, page, this, paramsStr);
		}
	}

	private void footerTextUpdate() {
		// mCustomListView.getFrooterLayout().finishView("数据加载完成");
		mCustomListView.getFrooterLayout().finishView("");
	}

	private void notData() {
		if (!isRefresh) {
			onFailure(null);
		} else {
			if (page == 1) {
				if (loadingLayout != null) {
					loadingLayout.showNoDataLayout(type);
				}
			}
			isLoadNetData = true;
			isShowData = true;
		}
	}

	@Override
	public void onCancelled(CancelledException e) {

	}

	@Override
	public void onError(Throwable e, boolean arg1) {
		onFailure(null);
	}

	@Override
	public void onFinished() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(String data) {
		try {
			if (data != null) {
				JSONObject json = JSONObject.parseObject(data);
				if (json != null) {
					int error = json.getIntValue("error");
					data = json.getString("data");
					if (page == 1) {
						totalPage = json.getIntValue("totalpage");
					}
					if (error == 100) {
						if (OtherUtils.isNotEmpty(data) && !"[]".equals(data)) {
							List<T> tmpList = null;
							try {
								tmpList = JSONObject.parseArray(data, mClass);
							} catch (Exception e) {
								LogUtils.showErrorMessage(e);
							}
							if (tmpList != null && tmpList.size() > 0) {
								if (page == 1) {
									reFreshTime = System.currentTimeMillis();
									ViewUtils.setVisibility(View.VISIBLE, mCustomListView);
									if (!isRefresh) {
										showList.clear();
										cacheList.clear();
										showList.addAll(tmpList);
										adapter.notifyDataSetChanged();
										if ((page >= totalPage && totalPage != 0) || tmpList.size() < 10) {
											footerTextUpdate();
										} else {
											page = 2;
											isLoadNetData = true;
											mCustomListView.getFrooterLayout().finishView(
													context.getString(R.string.custom_listview_footer_loading_info));
										}
										setUpdateView(R.string.custom_listview_head_info_ok);
										isRefresh = true;
									} else {
										showList.addAll(tmpList);
										adapter.setList(showList);
										adapter.notifyDataSetChanged();
										if (loadingLayout != null) {
											loadingLayout.hideAllLayout();
										}
										ViewUtils.setVisibility(View.VISIBLE, mCustomListView);
									}
								} else if (mCustomListView.isLastPosition() && cacheList.size() == 0) {
									showList.addAll(tmpList);
									adapter.notifyDataSetChanged();
								} else {
									cacheList.addAll(tmpList);
								}
								if ((page >= totalPage && totalPage != 0) || tmpList.size() < 10) {
									footerTextUpdate();
									isLoadNetData = false;
									return;
								}
								page++;
								isShowData = true;
								isLoadNetData = true;
								return;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtils.showErrorMessage(e);
		}
		notData();
	}

}

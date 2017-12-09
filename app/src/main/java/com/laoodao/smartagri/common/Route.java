package com.laoodao.smartagri.common;

import android.app.Activity;
import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.laoodao.smartagri.event.QAEvent;
import com.laoodao.smartagri.ui.discovery.activity.CottonBillActivity;
import com.laoodao.smartagri.ui.discovery.activity.CottonQueryActivity;
import com.laoodao.smartagri.ui.discovery.activity.EncyclopediaActivity;
import com.laoodao.smartagri.ui.discovery.activity.MenuMoreActivity;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopActivity;
import com.laoodao.smartagri.ui.discovery.activity.NewsDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.PriceDetailsActivity;
import com.laoodao.smartagri.ui.discovery.activity.PriceQuotationActivity;
import com.laoodao.smartagri.ui.discovery.activity.TruthQueryActivity;
import com.laoodao.smartagri.ui.farmland.activity.FarmlandManagerActivity;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.home.activity.WebViewActivity;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.market.dialog.ReleaseSupplyDemandDialog;
import com.laoodao.smartagri.ui.market.dialog.ReleaseSupplyDialog;
import com.laoodao.smartagri.ui.qa.activity.AskActivity;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.ui.user.activity.ContactsListActivity;
import com.laoodao.smartagri.ui.user.activity.FarmingCapitalActivity;
import com.laoodao.smartagri.ui.user.activity.IdeaFeedbackActivity;
import com.laoodao.smartagri.ui.user.activity.InviteActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.activity.MessageDetailActivity;
import com.laoodao.smartagri.ui.user.activity.OrderDetailActivity;
import com.laoodao.smartagri.ui.user.activity.RepaymentOrderActivity;
import com.laoodao.smartagri.ui.user.activity.ReturnGoodsDetailActivity;
import com.laoodao.smartagri.ui.user.activity.SettingActivity;
import com.laoodao.smartagri.ui.user.activity.SignInActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/10.
 */

public class Route {

    public static final Map<String, Object> map = new HashMap<>();

    public static final String MY_FARMLAND = "laoodao://user/farmland"; //我的农田  String title
    public static final String SHARE = "laoodao://user/share";        //劳道分享  String title
    public static final String IDEA_FEEDBACK = "laoodao://user/idea_feedback";//意见反馈  String title
    public static final String SETTING = "laoodao://user/setting";      //系统设置  String title
    public static final String FARMERS_MONEY_RECORD = "laoodao://user/farmers_money_recrod"; //农资记录  String title
    public static final String AUTHENTICITY_QUERY = "laoodao://market/authenticity_query";//真伪查询
    public static final String SIGN = "laoodao://home/sign";//每日签到
    public static final String MARKET_RELEASE = "laoodao://market/market_release";//供求发布
    public static final String QUESTIONS = "laoodao://answer/questions";//快速提问
    public static final String NEARBYSHOPS = "laoodao://home/nearby_shops";//附近的农资店
    public static final String ENCYCLOPEDIA = "laoodao://market/encyclopedia";//百科全书
    public static final String LOGIN = "laoodao://user/login";                  //登录
    public static final String ANSWER_DETAIL = "laoodao://answer/answer_detail";                            //问答详情  String id
    public static final String BUY_DETAIL = "laoodao://market/buy_detail";                                  //求购详情  String id
    public static final String SUPPLY_DETAIL = "laoodao://market/supply_detail";                            //供销详情  String id
    public static final String ANSWER_NEW = "laoodao://answer/answer_new";                   //问答列表最新
    public static final String ALL_MENU = "laoodao://discovery/all_menu";// 发现菜单全部
    public static final String NEW_DETAILS = "laoodao://market/news_detail";//新闻详情  String id
    public static final String COTTON = "laoodao://discovery/cotton";//棉花查询
    public static final String MSG_DETAIL = "laoodao://user/msg_detail";//消息详情 String id,String obj_type
    public static final String MY_ORDER = "laoodao://user/my_order";//我的订单，还款订单详情  String id
    public static final String REPAYMENT_ORDER = "laoodao://user/repayment_order"; //还款订单  String id
    public static final String RETURN_GOODS = "laoodao://user/return_goods";//退款详情  String id
    public static final String COTTON_BILL = "laoodao://discovery/cotton_bill";//棉花账单
    public static final String PRICE_QUOTATION = "laoodao://discovery/price_quotation"; //价格查询
    public static final String PRICE_QUOTATION_DETAIL = "laoodao://discovery/price_quotation_detail";//价格详情
    public static final String CHAT_LIST = "laoodao://user/emchat_list";//我的联系人


    // public static final String share = "laoodao://user/share";                        //劳道分享
//    laoodao://market/authentic_search_detail_pesticide?id=@String
//    laoodao://market/authentic_search_detail_fertilizer?id=@String
//    laoodao://market/authentic_search_detail_seed?id=@String
//    laoodao://market/authentic_search_detail_microbe?id=@String

    static {
        map.put(RETURN_GOODS, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String id = stringValue(path.params, "id");
                ReturnGoodsDetailActivity.start(context, Integer.parseInt(id));
            }
        });
        map.put(REPAYMENT_ORDER, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String id = stringValue(path.params, "id");
                RepaymentOrderActivity.start(context, Integer.parseInt(id));
            }
        });
        map.put(MY_ORDER, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String id = stringValue(path.params, "id");
                OrderDetailActivity.start(context, Integer.parseInt(id));
            }
        });
        map.put(SHARE, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String title = stringValue(path.params, "title");
                InviteActivity.start(context, title);
            }
        });
        map.put(MSG_DETAIL, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String id = stringValue(path.params, "id");
                String objType = stringValue(path.params, "obj_type");
                MessageDetailActivity.start(context, Integer.parseInt(id), objType);
            }
        });
        map.put(IDEA_FEEDBACK, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String title = stringValue(path.params, "title");
                IdeaFeedbackActivity.start(context, title);
            }
        });
        map.put(ALL_MENU, MenuMoreActivity.class);
        map.put(SETTING, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String title = stringValue(path.params, "title");
                SettingActivity.start(context, title);
            }
        });
        map.put(FARMERS_MONEY_RECORD, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                String title = stringValue(path.params, "title");
                FarmingCapitalActivity.start(context, title);
            }
        });
        map.put(NEW_DETAILS, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                int id = intValue(path.params, "id");
                NewsDetailActivity.start(context, id);
            }
        });
        map.put(PRICE_QUOTATION_DETAIL, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                int id = intValue(path.params, "id");
                PriceDetailsActivity.start(context, id);
            }
        });
        map.put(CHAT_LIST, ContactsListActivity.class);
        map.put(PRICE_QUOTATION, PriceQuotationActivity.class);
        map.put(COTTON_BILL, CottonBillActivity.class);
        map.put(SIGN, SignInActivity.class);
        map.put(NEARBYSHOPS, NearbyShopActivity.class);
        map.put(COTTON, CottonQueryActivity.class);
        map.put(QUESTIONS, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                AskActivity.start(context, "main");
            }
        });
        map.put(AUTHENTICITY_QUERY, TruthQueryActivity.class);
        //map.put(share, InviteActivity.class);
        map.put(ENCYCLOPEDIA, EncyclopediaActivity.class);
        map.put(ANSWER_NEW, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                EventBus.getDefault().post(new QAEvent(0));
                MainActivity.start(context, 1);
            }
        });
        map.put(SUPPLY_DETAIL, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                int id = intValue(path.params, "id");
                SupplyDetailsActivity.start(context, "供销详情", id);
            }
        });
        map.put(BUY_DETAIL, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                int id = intValue(path.params, "id");
                BuyDetailsActivity.start(context, "求购详情", id);
            }
        });
        map.put(ANSWER_DETAIL, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                int id = intValue(path.params, "id");
                QuestionDetailActivity.start(context, id);
            }
        });
        map.put(LOGIN, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                LoginActivity.start(context, true);
            }
        });
        map.put(MY_FARMLAND, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                int id = intValue(path.params, "id");
                String title = stringValue(path.params, "title");
                FarmlandManagerActivity.start(context, id, title);
            }
        });
        map.put(MARKET_RELEASE, new RouteHandler() {
            @Override
            public void handle(Context context, Path path) {
                new ReleaseSupplyDialog(context).show();
            }
        });
        //map.put(MY_FARMLAND, RecordActivity.class);

    }

    private static int intValue(UrlQuerySanitizer params, String name) {
        try {
            return Integer.valueOf(params.getValue(name));
        } catch (Exception e) {

        }
        return 0;
    }

    private static String stringValue(UrlQuerySanitizer params, String name) {
        try {
            return params.getValue(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void go(Context context, String url) {
        go(context, url, "");
    }

    public static void go(Context context, String url, String title) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        try {
            url = URLDecoder.decode(url, "utf-8");
        } catch (Exception e) {
        }
        if (url.startsWith("http://") || url.startsWith("https://")) {
            WebViewActivity.start(context, url);
            return;
        }
        if (!(url.startsWith("laoodao://"))) {
            return;
        }

        Path path = new Path(url);
        if (map.containsKey(path.route)) {
            Object handler = map.get(path.route);
            if (handler instanceof Class) {
                UiUtils.startActivity((Activity) context, (Class) handler);
            } else if (handler instanceof RouteHandler) {
                ((RouteHandler) handler).handle(context, path);
            }
        }
    }

    static class Path {
        String route;
        String query;
        String hash;
        UrlQuerySanitizer params;

        public Path(String url) {

            int queryIndex = url.indexOf('?');
            int hashIndex = url.indexOf('#');

            query = parseQuery(url, queryIndex, hashIndex);
            route = parseRouteId(url, queryIndex, hashIndex);

            if (hashIndex >= 0) {
                hash = url.substring(hashIndex);
            }

            params = new UrlQuerySanitizer();
            params.setAllowUnregisteredParamaters(true);
            params.setUnregisteredParameterValueSanitizer(sDefaultSanitizer);
            params.parseQuery(query);

        }

        String parseRouteId(String url, int queryIndex, int hashIndex) {
            String route = url;
            if (queryIndex >= 0) {
                route = url.substring(0, queryIndex);
            } else if (hashIndex >= 0) {
                route = url.substring(0, hashIndex);
            }
            if (route.endsWith("/")) {
                return route.substring(0, route.length() - 1);
            }
            return route;
        }

        String parseQuery(String url, int queryIndex, int hashIndex) {
            String query;
            if (queryIndex >= 0 && hashIndex >= 0) {
                query = url.substring(queryIndex + 1, hashIndex);
            } else if (queryIndex >= 0) {
                query = url.substring(queryIndex + 1);
            } else {
                query = "";
            }
            return query;
        }

    }

    static UrlQuerySanitizer.ValueSanitizer sDefaultSanitizer = new UrlQuerySanitizer.ValueSanitizer() {
        @Override
        public String sanitize(String value) {
            return value;
        }
    };


    static class MainHandler implements RouteHandler {
        int mIndex;
        static List<String> types = Arrays.asList("home", "mall", "msg", "my");

        public MainHandler(int index) {
            mIndex = index;
        }

        @Override
        public void handle(Context context, Path path) {
            Bundle bundle = new Bundle();
            if (path.params.hasParameter(Constant.TYPE)) {
                String type = path.params.getValue(Constant.TYPE);
                bundle.putInt(Constant.TYPE, types.indexOf(type));
            }
            bundle.putInt("tab", mIndex);

//            MainActivity.start(context, bundle);

            // MainActivity.start(context, bundle);

        }

    }

    interface RouteHandler {
        void handle(Context context, Path path);

    }
}

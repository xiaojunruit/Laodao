package com.laoodao.smartagri.di.component;

import com.laoodao.smartagri.di.scope.ActivityScope;
import com.laoodao.smartagri.ui.discovery.activity.BaikeSearchActivity;
import com.laoodao.smartagri.ui.discovery.activity.ConcernCropPriceActivity;
import com.laoodao.smartagri.ui.discovery.activity.CottonBillActivity;
import com.laoodao.smartagri.ui.discovery.activity.CottonCultivationActivity;
import com.laoodao.smartagri.ui.discovery.activity.CottonQueryActivity;
import com.laoodao.smartagri.ui.discovery.activity.CropDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.EncyclopediaActivity;
import com.laoodao.smartagri.ui.discovery.activity.FertilizerDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.FindCropActivity;
import com.laoodao.smartagri.ui.discovery.activity.MapFeedbackActivity;
import com.laoodao.smartagri.ui.discovery.activity.MapReportingErrorsActivity;
import com.laoodao.smartagri.ui.discovery.activity.MenuMoreActivity;
import com.laoodao.smartagri.ui.discovery.activity.MicrobialFertilizerActivity;
import com.laoodao.smartagri.ui.discovery.activity.MicrobialFertilizerDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.MyAttentionPriceActivity;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopActivity;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopMapActivity;
import com.laoodao.smartagri.ui.discovery.activity.NewsActivity;
import com.laoodao.smartagri.ui.discovery.activity.NewsDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.NewsSearchActivity;
import com.laoodao.smartagri.ui.discovery.activity.PesticideActivity;
import com.laoodao.smartagri.ui.discovery.activity.PesticideDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.PriceClassActivity;
import com.laoodao.smartagri.ui.discovery.activity.PriceDetailsActivity;
import com.laoodao.smartagri.ui.discovery.activity.PriceQuotationActivity;
import com.laoodao.smartagri.ui.discovery.activity.PriceQuotationSearchActivity;
import com.laoodao.smartagri.ui.discovery.activity.QueryFertilizerActivity;
import com.laoodao.smartagri.ui.discovery.activity.CottonQueryResultActivity;
import com.laoodao.smartagri.ui.discovery.activity.QuerySeedActivity;
import com.laoodao.smartagri.ui.discovery.activity.ReleaseFormulaActivity;
import com.laoodao.smartagri.ui.discovery.activity.SeedDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.SelectNewsMenuActivity;
import com.laoodao.smartagri.ui.discovery.activity.TruthQueryActivity;
import com.laoodao.smartagri.ui.discovery.activity.TruthQuerySearchActivity;
import com.laoodao.smartagri.ui.discovery.fragment.BaikeArticleFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CategoryFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CottonBillGuideFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CottonFiristBillFragment;
import com.laoodao.smartagri.ui.discovery.fragment.CottonMaxBillFragment;
import com.laoodao.smartagri.ui.discovery.fragment.DiscoverFragment;
import com.laoodao.smartagri.ui.discovery.fragment.EnterpriseInformationFragment;
import com.laoodao.smartagri.ui.discovery.fragment.FavoriteBusinessFragment;
import com.laoodao.smartagri.ui.discovery.fragment.InvitingFriendsFragment;
import com.laoodao.smartagri.ui.discovery.fragment.RecordBillFragment;
import com.laoodao.smartagri.ui.discovery.fragment.NewsListFragment;
import com.laoodao.smartagri.ui.discovery.fragment.NormalEnterpriseFragment;
import com.laoodao.smartagri.ui.discovery.fragment.ReportInformationFragment;
import com.laoodao.smartagri.ui.discovery.fragment.SevenChartFragment;

import dagger.Component;

/**
 * Created by 欧源 on 2017/4/20.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface DiscoveryComponent {

    EncyclopediaActivity inject(EncyclopediaActivity activity);

    DiscoverFragment inject(DiscoverFragment fragment);

    NewsActivity inject(NewsActivity activity);

    NewsListFragment inject(NewsListFragment fragment);

    TruthQueryActivity inject(TruthQueryActivity activity);

    NewsDetailActivity inject(NewsDetailActivity activity);

    NearbyShopDetailActivity inject(NearbyShopDetailActivity activity);

    NearbyShopActivity inject(NearbyShopActivity activity);

    TruthQuerySearchActivity inject(TruthQuerySearchActivity activity);

    CropDetailActivity inject(CropDetailActivity activity);

    QuerySeedActivity inject(QuerySeedActivity activity);

    QueryFertilizerActivity inject(QueryFertilizerActivity activity);

    MicrobialFertilizerActivity inject(MicrobialFertilizerActivity activity);

    PesticideActivity inject(PesticideActivity activity);

    MicrobialFertilizerDetailActivity inject(MicrobialFertilizerDetailActivity activity);

    SeedDetailActivity inject(SeedDetailActivity activity);

    PesticideDetailActivity inject(PesticideDetailActivity activity);

    FertilizerDetailActivity inject(FertilizerDetailActivity activity);

    SelectNewsMenuActivity inject(SelectNewsMenuActivity activity);

    CategoryFragment inject(CategoryFragment fragment);

    BaikeArticleFragment inject(BaikeArticleFragment fragment);

    ReleaseFormulaActivity inject(ReleaseFormulaActivity activity);

    FindCropActivity inject(FindCropActivity activity);

    NewsSearchActivity inject(NewsSearchActivity activity);

    BaikeSearchActivity inject(BaikeSearchActivity activity);

    MenuMoreActivity inject(MenuMoreActivity activity);

    CottonQueryActivity inject(CottonQueryActivity activity);

    ReportInformationFragment inject(ReportInformationFragment fragment);

    EnterpriseInformationFragment inject(EnterpriseInformationFragment fragment);

    CottonQueryResultActivity inject(CottonQueryResultActivity activity);

    NormalEnterpriseFragment inject(NormalEnterpriseFragment fragment);

    CottonCultivationActivity inject(CottonCultivationActivity activity);

    CottonBillActivity inject(CottonBillActivity activity);

    CottonBillGuideFragment inject(CottonBillGuideFragment fragment);

    RecordBillFragment inject(RecordBillFragment fragment);


    CottonMaxBillFragment inject(CottonMaxBillFragment fragment);

    CottonFiristBillFragment inject(CottonFiristBillFragment fragment);

    InvitingFriendsFragment inject(InvitingFriendsFragment fragment);

    FavoriteBusinessFragment inject(FavoriteBusinessFragment fragment);

    ConcernCropPriceActivity inject(ConcernCropPriceActivity activity);

    MyAttentionPriceActivity inject(MyAttentionPriceActivity activity);

    PriceDetailsActivity inject(PriceDetailsActivity activity);

    SevenChartFragment inject(SevenChartFragment fragment);

    PriceQuotationActivity inject(PriceQuotationActivity activity);

    PriceQuotationSearchActivity inject(PriceQuotationSearchActivity activity);

    PriceClassActivity inject(PriceClassActivity activity);

    NearbyShopMapActivity inject(NearbyShopMapActivity activity);

    MapReportingErrorsActivity inject(MapReportingErrorsActivity activity);

    MapFeedbackActivity inject(MapFeedbackActivity activity);

}

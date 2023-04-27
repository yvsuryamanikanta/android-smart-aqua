package com.odos.smartaqua.tank;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.BaseObservable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.Gson;
import com.odos.smartaqua.API.ServiceAsyncResponse;
import com.odos.smartaqua.API.ServiceConstants;
import com.odos.smartaqua.API.VolleyService;
import com.odos.smartaqua.R;
import com.odos.smartaqua.databinding.FragmentPondlistBinding;
import com.odos.smartaqua.feed.FeedListAdapter;
import com.odos.smartaqua.feed.FeedModel;
import com.odos.smartaqua.prelogin.sighnup.UserRoles;
import com.odos.smartaqua.prelogin.sighnup.UserRolesAdapter;
import com.odos.smartaqua.utils.AquaConstants;
import com.odos.smartaqua.utils.CheckNetwork;
import com.odos.smartaqua.utils.Helper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PondListFragmentViewModel extends BaseObservable implements ServiceAsyncResponse, CultureAdapter.ClickListener, StockingAdapter.ClickListener {

    private Context _context;
    private FragmentPondlistBinding _fragmentBinding;
    private String[] titles;
    private int[] icons;
    private ServiceAsyncResponse serviceAsyncResponse;
    private ArrayList<UserRoles> userRolesArrayList;
    private String tankId, cultureId, tankName, cultureResponse;
    private int tankPosition;
    private List<EventDay> events;

    public PondListFragmentViewModel(int position, Context context, FragmentPondlistBinding pondlistBinding, String tId, String cId, String tName, String response) {
        this._context = context;
        this.tankPosition = position;
        this.tankId = tId;
        this.cultureId = cId;
        this.tankName = tName;
        this.cultureResponse = response;
        this._fragmentBinding = pondlistBinding;
        serviceAsyncResponse = (ServiceAsyncResponse) this;
    }

    public void loadData() {
        if (CheckNetwork.isNetworkAvailable(_context)) {
            VolleyService.volleyGetRequest(_context, _context.getString(R.string.jsonobjectrequest),
                    ServiceConstants.TANK_INFO + tankId, null, Helper.headerParams(_context),
                    (ServiceAsyncResponse) serviceAsyncResponse, 1, true);

        } else {
            Helper.showMessage(_context, _context.getString(R.string.internetchecking), AquaConstants.FINISH);
        }

    }


    @Override
    public void stringResponse(String service, String response, int serviceno) {

    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void jsonObjectResponse(String service, JSONObject jsonObject, int serviceno) {
        switch (serviceno) {
            case 1:
                try {
                    String status = jsonObject.getString("status");
                    String response = jsonObject.getString("response");
                    if (status.equalsIgnoreCase("Sucess")) {
                        Log.e("#######", new Gson().toJson(response));
                        JSONObject jsonObject1 = new JSONObject(response);
                        String tanklocation = jsonObject1.getString("tanklocation");
                        String tankimage = jsonObject1.getString("tankimage");
                        String tankSize = jsonObject1.getString("tankSize");
                        String tankSizeType = jsonObject1.getString("tankSizeType");
                        String latitude = jsonObject1.getString("latitude");
                        String longitude = jsonObject1.getString("longitude");
                        _fragmentBinding.txtTankAddress.setText(tanklocation);
                        _fragmentBinding.txtTankSize.setText(tankSize);

                        ArrayList<CultureModel> cultureModelArrayList = new ArrayList<>();
                        JSONArray preparationDTOList = jsonObject1.getJSONArray("preparationDTOList");
                        if (preparationDTOList.length() == 0) {
                            _fragmentBinding.txtPreparation.setVisibility(View.GONE);
                        } else {
                            _fragmentBinding.txtPreparation.setVisibility(View.VISIBLE);
                            for (int a = 0; a < preparationDTOList.length(); a++) {
                                JSONObject preparationJSONObject = preparationDTOList.getJSONObject(a);
                                String previousdecease = preparationJSONObject.getString("previousdecease");
                                String recordkeeping = preparationJSONObject.getString("recordkeeping");
                                String drying = preparationJSONObject.getString("drying");
                                String biosecurity = preparationJSONObject.getString("biosecurity");
                                String scrapping = preparationJSONObject.getString("scrapping");
                                String ploughing = preparationJSONObject.getString("ploughing");
                                String liming = preparationJSONObject.getString("liming");
                                String soilcheck = preparationJSONObject.getString("soilcheck");
                                String fillingwatertype = preparationJSONObject.getString("fillingwatertype");
                                String watersource = preparationJSONObject.getString("watersource");
                                String pondtype = preparationJSONObject.getString("pondtype");
                                String bleaching = preparationJSONObject.getString("bleaching");
                                String minerals = preparationJSONObject.getString("minerals");
                                String probiotics = preparationJSONObject.getString("probiotics");
                                String filteration = preparationJSONObject.getString("filteration");
                                String fertilization = preparationJSONObject.getString("fertilization");
                                String ehp = preparationJSONObject.getString("ehp");

                                CultureModel cultureModel = new CultureModel(previousdecease
                                        , recordkeeping
                                        , drying
                                        , biosecurity
                                        , scrapping
                                        , ploughing
                                        , liming
                                        , soilcheck
                                        , fillingwatertype
                                        , watersource
                                        , pondtype
                                        , bleaching
                                        , minerals
                                        , probiotics
                                        , filteration
                                        , fertilization
                                        , ehp);
                                cultureModelArrayList.add(cultureModel);
                            }
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(_context);
                            _fragmentBinding.recyclerCulture.setLayoutManager(mLayoutManager);
                            _fragmentBinding.recyclerCulture.setAdapter(new CultureAdapter(_context, cultureModelArrayList, this));
                        }

                        ArrayList<StockingModel> stockingModelArrayList = new ArrayList<>();
                        JSONArray stockingDTOList = jsonObject1.getJSONArray("stockingDTOList");
                        if (stockingDTOList.length() == 0) {
                            _fragmentBinding.txtStocking.setVisibility(View.GONE);
                        } else {
                            _fragmentBinding.txtStocking.setVisibility(View.VISIBLE);
                            for (int b = 0; b < stockingDTOList.length(); b++) {
                                JSONObject stockingJSONObject = stockingDTOList.getJSONObject(b);
                                String ammonia = stockingJSONObject.getString("ammonia");
                                String nitrite = stockingJSONObject.getString("nitrite");
                                String alkalnity = stockingJSONObject.getString("alkalnity");
                                String hardness = stockingJSONObject.getString("hardness");
                                String iron = stockingJSONObject.getString("iron");
                                String mineral = stockingJSONObject.getString("mineral");
                                String clorine = stockingJSONObject.getString("clorine");
                                String salnity = stockingJSONObject.getString("salnity");
                                String transparancy = stockingJSONObject.getString("transparancy");
                                String watercolor = stockingJSONObject.getString("watercolor");
                                String waterdepth = stockingJSONObject.getString("waterdepth");
                                String plsize = stockingJSONObject.getString("plsize");
                                String plpcrresult = stockingJSONObject.getString("plpcrresult");
                                String plpackingdensity = stockingJSONObject.getString("plpackingdensity");
                                String plage = stockingJSONObject.getString("plage");
                                String hepathopancreasCondition = stockingJSONObject.getString("hepathopancreasCondition");
                                String avgnoofplPerBag = stockingJSONObject.getString("avgnoofplPerBag");
                                String acclinitization = stockingJSONObject.getString("acclinitization");
                                String seedtrnsportationtime = stockingJSONObject.getString("seedtrnsportationtime");
                                String vmodeoftransport = stockingJSONObject.getString("vmodeoftransport");

                                StockingModel stockingModel = new StockingModel(
                                        ammonia,
                                        nitrite,
                                        alkalnity,
                                        hardness,
                                        iron,
                                        mineral,
                                        clorine,
                                        salnity,
                                        transparancy,
                                        watercolor,
                                        waterdepth,
                                        plsize,
                                        plpcrresult,
                                        plpackingdensity,
                                        plage,
                                        hepathopancreasCondition,
                                        avgnoofplPerBag,
                                        acclinitization,
                                        seedtrnsportationtime,
                                        vmodeoftransport);
                                stockingModelArrayList.add(stockingModel);
                            }
                            RecyclerView.LayoutManager mLayoutMng = new LinearLayoutManager(_context);
                            _fragmentBinding.recyclerStock.setLayoutManager(mLayoutMng);
                            _fragmentBinding.recyclerStock.setAdapter(new StockingAdapter(_context, stockingModelArrayList, this));
                        }
//                        _fragmentBinding.txtTankAddress.setText(tanklocation);
//                        Picasso.with(_context).load(tankimage).into(_fragmentBinding.tankImage);
//                        _fragmentBinding.txtTankSize.setText(tankSize + " in " + tankSizeType);
//                        _fragmentBinding.txtDecease.setText(previousdecease);
//                        _fragmentBinding.txtRecordKeeping.setText(recordkeeping);
//                        _fragmentBinding.txtDrying.setText(drying);
//                        _fragmentBinding.txtBioSecurity.setText(biosecurity);
//                        _fragmentBinding.txtScrapping.setText(scrapping);
//                        _fragmentBinding.txtPloughing.setText(ploughing);
//                        _fragmentBinding.txtLiming.setText(liming);
//                        _fragmentBinding.txtSoilCheck.setText(soilcheck);
//                        _fragmentBinding.txtFillingWater.setText(fillingwatertype);
//                        _fragmentBinding.txtSourceWater.setText(watersource);
//                        _fragmentBinding.txtPondType.setText(pondtype);
//                        _fragmentBinding.txtAmmonia.setText(ammonia);
//                        _fragmentBinding.txtNitrite.setText(nitrite);
//                        _fragmentBinding.txtAlkalnity.setText(alkalnity);
//                        _fragmentBinding.txtHardness.setText(hardness);
//                        _fragmentBinding.txtIron.setText(iron);
//                        _fragmentBinding.txtMineral.setText(mineral);
//                        _fragmentBinding.txtClorine.setText(clorine);
//                        _fragmentBinding.txtSalnity.setText(salnity);
//                        _fragmentBinding.txtTransparency.setText(transparancy);
                    }
                } catch (Exception e) {
                    Helper.showMessage(_context, "something went wrong please restart app once." + e, AquaConstants.HOLD);
                }
                break;

        }

    }

    @Override
    public void jsonArrayResponse(String service, JSONArray jsonarray, int serviceno) {

    }

    @Override
    public void onClicked(CultureModel CultureModel, int pos) {

    }

    @Override
    public void onClicked(StockingModel StockingModel, int pos) {

    }
}
package com.yifactory.daocheapp.widget.addressSelected.picker;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.yifactory.daocheapp.widget.addressSelected.entity.City;
import com.yifactory.daocheapp.widget.addressSelected.entity.County;
import com.yifactory.daocheapp.widget.addressSelected.entity.Province;
import com.yifactory.daocheapp.widget.addressSelected.widget.WheelView;

import java.util.ArrayList;
import java.util.List;


public class AddressPicker extends LinkagePicker<Province, City, County> {
    private OnAddressPickListener onAddressPickListener;
    private OnWheelListener onWheelListener;
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    private ArrayList<Province> provinces = new ArrayList<>();

    public AddressPicker(Activity activity, ArrayList<Province> provinces) {
        super(activity, new AddressProvider(provinces));
        this.provinces = provinces;
    }

    public void setSelectedItem(Province province, City city, County county) {
        super.setSelectedItem(province, city, county);
    }

    public void setSelectedItem(String province, String city, String county) {
        setSelectedItem(new Province(province), new City(city), new County(county));
    }

    @NonNull
    public Province getSelectedProvince() {
        return provinces.get(selectedFirstIndex);
    }

    @Nullable
    public City getSelectedCity() {
        List<City> cities = getSelectedProvince().getCities();
        if (cities.size() == 0) {
            return null;//可能没有第二级数据
        }
        return cities.get(selectedSecondIndex);
    }

    @Nullable
    public County getSelectedCounty() {
        City selectedCity = getSelectedCity();
        if (selectedCity == null) {
            return null;
        }
        List<County> counties = selectedCity.getCounties();
        if (counties.size() == 0) {
            return null;//可能没有第三级数据
        }
        return counties.get(selectedThirdIndex);
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setOnWheelListener(OnWheelListener onWheelListener) {
        this.onWheelListener = onWheelListener;
    }

    public void setOnAddressPickListener(OnAddressPickListener listener) {
        this.onAddressPickListener = listener;
    }

    @Deprecated
    @Override
    public final void setOnLinkageListener(OnLinkageListener onLinkageListener) {
        throw new UnsupportedOperationException("Please use setOnAddressPickListener instead.");
    }

    @NonNull
    @Override
    protected View makeCenterView() {
        if (null == provider) {
            throw new IllegalArgumentException("please set address provider before make view");
        }
        float provinceWeight = firstColumnWeight;
        float cityWeight = secondColumnWeight;
        float countyWeight = thirdColumnWeight;
        if (hideCounty) {
            hideProvince = false;
        }
        if (hideProvince) {
            provinceWeight = 0;
            cityWeight = firstColumnWeight;
            countyWeight = secondColumnWeight;
        }
        dividerConfig.setRatio(WheelView.DividerConfig.FILL);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);

        final WheelView provinceView = createWheelView();
        provinceView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, provinceWeight));
        layout.addView(provinceView);
        if (hideProvince) {
            provinceView.setVisibility(View.GONE);
        }

        final WheelView cityView = createWheelView();
        cityView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, cityWeight));
        layout.addView(cityView);

        final WheelView countyView = createWheelView();
        countyView.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, countyWeight));
        layout.addView(countyView);
        if (hideCounty) {
            countyView.setVisibility(View.GONE);
        }

        provinceView.setItems(provider.initFirstData(), selectedFirstIndex);
        provinceView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                selectedFirstIndex = index;
                selectedFirstItem = getSelectedProvince();
                if (onWheelListener != null) {
                    onWheelListener.onProvinceWheeled(selectedFirstIndex, selectedFirstItem);
                }
                selectedSecondIndex = 0;
                selectedThirdIndex = 0;
                List<City> cities = provider.linkageSecondData(selectedFirstIndex);
                if (cities.size() > 0) {
                    selectedSecondItem = cities.get(selectedSecondIndex);
                    cityView.setItems(cities, selectedSecondIndex);
                } else {
                    selectedSecondItem = null;
                    cityView.setItems(new ArrayList<String>());
                }
                List<County> counties = provider.linkageThirdData(selectedFirstIndex, selectedSecondIndex);
                if (counties.size() > 0) {
                    selectedThirdItem = counties.get(selectedThirdIndex);
                    countyView.setItems(counties, selectedThirdIndex);
                } else {
                    selectedThirdItem = null;
                    countyView.setItems(new ArrayList<String>());
                }
            }
        });

        cityView.setItems(provider.linkageSecondData(selectedFirstIndex), selectedSecondIndex);
        cityView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                selectedSecondIndex = index;
                selectedSecondItem = getSelectedCity();
                if (onWheelListener != null) {
                    onWheelListener.onCityWheeled(selectedSecondIndex, selectedSecondItem);
                }
                selectedThirdIndex = 0;
                List<County> counties = provider.linkageThirdData(selectedFirstIndex, selectedSecondIndex);
                if (counties.size() > 0) {
                    selectedThirdItem = counties.get(selectedThirdIndex);
                    countyView.setItems(counties, selectedThirdIndex);
                } else {
                    selectedThirdItem = null;
                    countyView.setItems(new ArrayList<String>());
                }
            }
        });

        countyView.setItems(provider.linkageThirdData(selectedFirstIndex, selectedSecondIndex), selectedThirdIndex);
        countyView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
            @Override
            public void onSelected(int index) {
                selectedThirdIndex = index;
                selectedThirdItem = getSelectedCounty();
                if (onWheelListener != null) {
                    onWheelListener.onCountyWheeled(selectedThirdIndex, selectedThirdItem);
                }
            }
        });
        return layout;
    }

    @Override
    public void onSubmit() {
        if (onAddressPickListener != null) {
            Province province = getSelectedProvince();
            City city = getSelectedCity();
            County county = null;
            if (!hideCounty) {
                county = getSelectedCounty();
            }
            onAddressPickListener.onAddressPicked(province, city, county);
        }
    }

    public interface OnAddressPickListener {

        void onAddressPicked(Province province, City city, County county);

    }

    public interface OnWheelListener {

        void onProvinceWheeled(int index, Province province);

        void onCityWheeled(int index, City city);

        void onCountyWheeled(int index, County county);

    }

    private static class AddressProvider implements Provider<Province, City, County> {
        private List<Province> firstList = new ArrayList<>();
        private List<List<City>> secondList = new ArrayList<>();
        private List<List<List<County>>> thirdList = new ArrayList<>();

        AddressProvider(List<Province> provinces) {
            parseData(provinces);
        }

        @Override
        public boolean isOnlyTwo() {
            return false;
        }

        @Override
        @NonNull
        public List<Province> initFirstData() {
            return firstList;
        }

        @Override
        @NonNull
        public List<City> linkageSecondData(int firstIndex) {
            if (secondList.size() <= firstIndex) {
                return new ArrayList<>();
            }
            return secondList.get(firstIndex);
        }

        @Override
        @NonNull
        public List<County> linkageThirdData(int firstIndex, int secondIndex) {
            if (thirdList.size() <= firstIndex) {
                return new ArrayList<>();
            }
            List<List<County>> lists = thirdList.get(firstIndex);
            if (lists.size() <= secondIndex) {
                return new ArrayList<>();
            }
            return lists.get(secondIndex);
        }

        private void parseData(List<Province> data) {
            int provinceSize = data.size();
            for (int x = 0; x < provinceSize; x++) {
                Province pro = data.get(x);
                firstList.add(pro);
                List<City> cities = pro.getCities();
                List<City> xCities = new ArrayList<>();
                List<List<County>> xCounties = new ArrayList<>();
                int citySize = cities.size();
                for (int y = 0; y < citySize; y++) {
                    City cit = cities.get(y);
                    cit.setProvinceId(pro.getAreaId());
                    xCities.add(cit);
                    List<County> counties = cit.getCounties();
                    ArrayList<County> yCounties = new ArrayList<>();
                    int countySize = counties.size();
                    for (int z = 0; z < countySize; z++) {
                        County cou = counties.get(z);
                        cou.setCityId(cit.getAreaId());
                        yCounties.add(cou);
                    }
                    xCounties.add(yCounties);
                }
                secondList.add(xCities);
                thirdList.add(xCounties);
            }
        }
    }
}

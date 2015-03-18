package com.oxygen8.net;

import java.util.ArrayList;
import java.util.List;

import com.oxygen8.net.fragments.AnimalPetsFragment;
import com.oxygen8.net.fragments.BarsFragment;
import com.oxygen8.net.fragments.BeautyFashionFragment;
import com.oxygen8.net.fragments.ElectronicsFragment;
import com.oxygen8.net.fragments.HobbiesFragment;
import com.oxygen8.net.fragments.HomeFurnitureFrament;
import com.oxygen8.net.fragments.HotelsFragment;
import com.oxygen8.net.fragments.JobsFragment;
import com.oxygen8.net.fragments.MobilePhonesFragment;
import com.oxygen8.net.fragments.PersonalsFragment;
import com.oxygen8.net.fragments.RealEstateFragment;
import com.oxygen8.net.fragments.RestaurantFragment;
import com.oxygen8.net.fragments.SchoolsFragment;
import com.oxygen8.net.fragments.VechiclesFragment;
import com.oxygen8.net.materialmenu.MaterialMenuIconSherlock;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SlidingMenuFragment extends Fragment implements OnChildClickListener{
	
	private ExpandableListView sectionListView;
	MaterialMenuIconSherlock materialMenu;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
       
        List<Section> sectionList = createMenu();
               
        View view = inflater.inflate(R.layout.slidingmenu_fragment, container, false);
        this.sectionListView = (ExpandableListView) view.findViewById(R.id.slidingmenu_view);
        this.sectionListView.setGroupIndicator(null);
       
        SectionListAdapter sectionListAdapter = new SectionListAdapter(this.getActivity(), sectionList);
        this.sectionListView.setAdapter(sectionListAdapter);
       
        this.sectionListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
        	
              @Override
              public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
              }
            });
       
        this.sectionListView.setOnChildClickListener(this);
       
        int count = sectionListAdapter.getGroupCount();
        
        for (int position = 0; position < count; position++) {
        	
            this.sectionListView.expandGroup(position);
        }
       
        return view;
    }

    private List<Section> createMenu() {
        List<Section> sectionList = new ArrayList<Section>();

        Section oDemoSection = new Section("Home");
        oDemoSection.addSectionItem(101,"Post Ad", "ic_ab_post");
        oDemoSection.addSectionItem(102, "Settings", "setting_white_icon");
        oDemoSection.addSectionItem(103, "Home", "ic_action_home");
       
        Section oGeneralSection = new Section("Categories");
        oGeneralSection.addSectionItem(201, "MobilePhones-Tablets", "estrella");
        oGeneralSection.addSectionItem(202, "Electronics-Video", "estrella");
        oGeneralSection.addSectionItem(203, "Home-Furniture-Garden", "estrella");
        oGeneralSection.addSectionItem(204, "Fashion-Beauty", "estrella");
        oGeneralSection.addSectionItem(205, "Hobbies-Art-Sport", "estrella");
        oGeneralSection.addSectionItem(206, "Animals-Pets", "estrella");
        oGeneralSection.addSectionItem(207, "Vechicles", "estrella");
        oGeneralSection.addSectionItem(208, "RealEstate", "estrella");
        oGeneralSection.addSectionItem(209, "Jobs-Services", "estrella");
        oGeneralSection.addSectionItem(210, "Restaurants", "estrella");
        oGeneralSection.addSectionItem(211, "Bars", "estrella");
        oGeneralSection.addSectionItem(212, "Hotels", "estrella");
        oGeneralSection.addSectionItem(213, "Schools", "estrella");
        oGeneralSection.addSectionItem(214, "Directory-Personals", "estrella");
        sectionList.add(oDemoSection);
        sectionList.add(oGeneralSection);
        return sectionList;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
            int groupPosition, int childPosition, long id) {

        switch ((int)id) {
        
        case 101:
            //TODO
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
        	Intent intent = new Intent(getActivity(),MainActivity.class);
        	startActivity(intent);
        	
            break;
            
        case 102:
            //TODO
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            break;
            
        case 103:
        	
        	Intent home = new Intent(getActivity(),HomeActivity.class);
        	startActivity(home);
        	
            break;
            
        case 201:
            //TODO
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            Intent mobile = new Intent(getActivity(),MobilePhonesFragment.class);
            startActivity(mobile);
            
            break;
            
        case 202:
            //TODO
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
        	Intent electronics = new Intent(getActivity(), ElectronicsFragment.class);
        	startActivity(electronics);
        	
            break;
            
        case 203:
            //TODO
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
        	Intent homePage = new Intent(getActivity(),HomeFurnitureFrament.class);
        	startActivity(homePage);
        	
            break;
            
        case 204:
        	
            //TODO
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
        	Intent fashion = new Intent(getActivity(),BeautyFashionFragment.class);
        	startActivity(fashion);
        
            break;
            
        case 205:
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
        	Intent hobbies = new Intent(getActivity(),HobbiesFragment.class);
        	startActivity(hobbies);
        	
        	break;
        	
        case 206:
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
        	Intent Pets = new Intent(getActivity(),AnimalPetsFragment.class);
        	startActivity(Pets);
        	
        	break;
        	
        case 207:
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            Intent Vechicles = new Intent(getActivity(),VechiclesFragment.class);
            startActivity(Vechicles);
            
        	break;
        	
        case 208:
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            Intent RealEstate = new Intent(getActivity(),RealEstateFragment.class);
            startActivity(RealEstate);
            
        	break;
        case 209:
//        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            Intent jobs = new Intent(getActivity(),JobsFragment.class);
            startActivity(jobs);
            
        	break;
        	
        case 210:
        	
        	Intent bars = new Intent(getActivity(),BarsFragment.class);
        	startActivity(bars);
        	
        	break;
        	
        case 211:
        	
        	Intent hotels = new Intent(getActivity(),HotelsFragment.class);
        	startActivity(hotels);
        	
        	break;
        	
        case 212:
        	
        	Intent personals = new Intent(getActivity(),PersonalsFragment.class);
        	startActivity(personals);
        	
        	break;
        	
        case 213:
        	
        	Intent restaurant = new Intent(getActivity(),RestaurantFragment.class);
        	startActivity(restaurant);
        	break;
        	
        case 214:
        	
        	Intent schools = new Intent(getActivity(),SchoolsFragment.class);
        	startActivity(schools);
        	
        	break;
        }
       
        return false;
    }

}

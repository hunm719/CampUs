package com.campus.campus.search.service;

import com.campus.campus.dataapi.entity.CampBaseInfo;
import com.campus.campus.dataapi.repository.SaveCampRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchService {


    private final SaveCampRepository saveCampRepository;

    public SearchService(SaveCampRepository saveCampRepository) {
        this.saveCampRepository = saveCampRepository;
    }
/*
    private final StoreRepository storeRepository;

    public SearchService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }*/

    public CampBaseInfo findById(long storeId){

        System.out.println("service > findById()");

        Optional<CampBaseInfo> campBaseInfo = saveCampRepository.findById(storeId);
        System.out.println(campBaseInfo.get().getStoreName());
        return campBaseInfo.get();
    }

    public List<CampBaseInfo> findAll(){

        System.out.println("service > findAll()");

        List<CampBaseInfo> campBaseInfos=saveCampRepository.findAll();

        return campBaseInfos;
    }

   /* public List<SearchStore> stubFindAll(){
        System.out.println("service > stub findAll()");

        // data.sql -> 인텔리제이
        // 더미 데이터 넣을때~!
        List<SearchStore> searchStores = new ArrayList<>();
        searchStores.add(new SearchStore(
                1L, "캠핑장1","카라반","경기도","화성시",
                "0","20","0","0","0","4","6","운동장,강/물놀이,농어촌체험시설",
                "Y","Y","Y","Y", "전기,무선인터넷,장작판매,온수,트렘폴린,물놀이장,놀이터,산책로,운동시설,마트.편의점",
                "강", "Y"
        ));

        searchStores.add(new SearchStore(
                2L, "캠핑장2","글램핑"," ","서울특별시",
                "0","20","0","0","0","4","6","운동장,강/물놀이,농어촌체험시설",
                "Y","Y","N","Y", "전기,무선인터넷,장작판매,온수,트렘폴린,물놀이장,놀이터,산책로,운동시설,마트.편의점",
                "호수", "Y"
        ));

        searchStores.add(new SearchStore(
                3L, "캠핑장3","일반야영장"," ","부산광역시",
                "0","0","10","10","0","4","6","운동장,강/물놀이,농어촌체험시설",
                "Y","Y","N","N", "전기,무선인터넷,장작판매,온수,트렘폴린,물놀이장,놀이터,산책로,운동시설,마트.편의점",
                "강, 호수", "N"
        ));

        return searchStores;

    }*/
}

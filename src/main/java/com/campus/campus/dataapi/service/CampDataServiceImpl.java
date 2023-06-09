package com.campus.campus.dataapi.service;

import com.campus.campus.dataapi.dto.LoadDataResponseDto;
import com.campus.campus.dataapi.dto.SaveCampRequestDto;
import com.campus.campus.dataapi.entity.CampBaseInfo;
import com.campus.campus.dataapi.exception.DataLoadFailedException;
import com.campus.campus.dataapi.exception.WrongURLException;
import com.campus.campus.dataapi.repository.SaveCampRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class CampDataServiceImpl implements CampDataService {

    public final SaveCampRepository campRepository;

    public LoadDataResponseDto loadAndSaveFromApiWithJson() {
        try {
            int page = 1;
            int numOfRows = 100;
            String apiUrl = "https://apis.data.go.kr/B551011/GoCamping/basedList?MobileOS=WIN&MobileApp=TadakTadak&_type=json";
            String apikey = "eqzJCAvqSy0VmYJ77GE51mGpqo4PFub0OrAs%2Fhw1S0COTrvYFwPULfG4K%2Bixr0uYch4uw3ciXr4PhRI%2F%2FdDQ%2FQ%3D%3D";

            int totalCnt = 0;
            int savedDataAmount = 0;

            boolean isLastPage = false;

            while (!isLastPage) {
                URL url = new URL(apiUrl + "&serviceKey=" + apikey + "&pageNo=" + page + "&numOfRows=" + numOfRows);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("content-type", "application/json");

                BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
                StringBuffer result = new StringBuffer();
                result.append(bf.readLine());
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(result.toString());

                checkURLKey(jsonObject);

                JSONObject response = (JSONObject) jsonObject.get("response");
                JSONObject body = (JSONObject) response.get("body");
                JSONObject item = (JSONObject) body.get("items");

                JSONArray jsonArray = (JSONArray) item.get("item");

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = (JSONObject) jsonArray.get(i);

                    Long store_id = Long.parseLong(object.get("contentId").toString());
                    String store_name = (String) object.get("facltNm");
                    String biz_num = (String) object.get("bizrno");
                    String main_img = (String) object.get("firstImageUrl");
                    String simple_info = (String) object.get("lineIntro");
                    String detail_info = (String) object.get("intro");
                    String store_phone = (String) object.get("tel");
                    String site_url = (String) object.get("homepage");
                    String category = (String) object.get("induty");
                    String location = (String) object.get("addr1");
                    String doNm = (String) object.get("doNm");
                    String sigunguNm = (String) object.get("sigunguNm");
                    String zipcode = (String) object.get("zipcode");
                    Double latitude = Double.parseDouble(object.get("mapX").toString());
                    Double longitude = Double.parseDouble(object.get("mapY").toString());
                    String direction_info = (String) object.get("direction");
                    String amenities = (String) object.get("sbrsCl");
                    String surroundings = (String) object.get("lctCl");
                    String surrFacilities = (String) object.get("posblFcltyCl");
                    String glamping_facility = (String) object.get("glampInnerFclty");
                    String caravan_facility = (String) object.get("caravInnerFclty");
                    String operating_season = (String) object.get("operPdCl");
                    String operating_date = (String) object.get("operDeCl");
                    Integer toilet_cnt = Integer.parseInt(object.get("toiletCo").toString());
                    Integer swrm_cnt = Integer.parseInt(object.get("swrmCo").toString());
                    Integer wtrpl_cnt = Integer.parseInt(object.get("wtrplCo").toString());
                    String animal_yn = (String) object.get("animalCmgCl");
                    String camp_feature = (String) object.get("featureNm");
                    Integer grass = Integer.parseInt(object.get("siteBottomCl1").toString());
                    Integer crushStone = Integer.parseInt(object.get("siteBottomCl2").toString());
                    Integer tech = Integer.parseInt(object.get("siteBottomCl3").toString());
                    Integer pebble = Integer.parseInt(object.get("siteBottomCl4").toString());
                    Integer soil = Integer.parseInt(object.get("siteBottomCl5").toString());
                    String caravanAc = (String) object.get("caravAcmpnyAt");
                    String trailerAc = (String) object.get("trlerAcmpnyAt");
                    String eqpRental = (String) object.get("eqpmnLendCl");
                    String exprn_yn = (String) object.get("exprnProgrmAt");
                    String exprn = (String) object.get("exprn");


                    //SaveCampRequestDto saveCampRequestDto = new SaveCampRequestDto(store_id, store_name, biz_num, main_img, simple_info, detail_info, store_phone, site_url, category, location, doNm, sigunguNm, zipcode, latitude, longitude, direction_info, basic_facility, surroundings, surround_info, glamping_facility, caravan_facility, operating_season, operating_date, toilet_cnt, swrm_cnt, wtrpl_cnt, animal_yn, camp_feature, siteBottomCl1, siteBottomCl2, siteBottomCl3, siteBottomCl4, siteBottomCl5, exprn_yn, exprn);

                    CampBaseInfo campBaseInfo = CampBaseInfo.builder()
                            .storeId(store_id)
                            .storeName(store_name)
                            .bizNum(biz_num)
                            .mainImg(main_img)
                            .simpleInfo(simple_info)
                            .detailInfo(detail_info)
                            .storePhone(store_phone)
                            .siteUrl(site_url)
                            .category(category)
                            .location(location)
                            .doNm(doNm)
                            .sigunguNm(sigunguNm)
                            .zipcode(zipcode)
                            .latitude(latitude)
                            .longitude(longitude)
                            .directionInfo(direction_info)
                            .amenities(amenities)
                            .surroundings(surroundings)
                            .surrFacilities(surrFacilities)
                            .glampingFacility(glamping_facility)
                            .caravanFacility(caravan_facility)
                            .operatingSeason(operating_season)
                            .operatingDate(operating_date)
                            .toiletCnt(toilet_cnt)
                            .swrmCnt(swrm_cnt)
                            .wtrplCnt(wtrpl_cnt)
                            .animalYn(animal_yn)
                            .campFeature(camp_feature)
                            .grass(grass)
                            .crushStone(crushStone)
                            .tech(tech)
                            .pebble(pebble)
                            .soil(soil)
                            .caravanAc(caravanAc)
                            .trailerAc(trailerAc)
                            .eqpRental(eqpRental)
                            .exprnYn(exprn_yn)
                            .exprn(exprn)
                            .build();

                    campRepository.save(campBaseInfo);
                }
                totalCnt = ((Number) body.get("totalCount")).intValue();
                // 현재 페이지가 마지막 페이지인지 확인합니다.
                if (page * numOfRows >= totalCnt) {
                    isLastPage = true;
                } else {
                    page++; // 다음 페이지로 이동합니다.
                }
            }
            savedDataAmount = ((Number) campRepository.count()).intValue(); // 현재 db에 저장된 데이터 갯수

            log.info("Page: {}", page);
            log.info("totalCnt: {} & savedDataAmount : {}", totalCnt, savedDataAmount);
            return new LoadDataResponseDto(totalCnt == savedDataAmount);

        } catch (Exception e) {
            throw new DataLoadFailedException(e);
        }
    }
    private void checkURLKey(JSONObject jsonObject) {
        if (jsonObject.get("code") != null && (int) jsonObject.get("code") == -4) {
            throw new WrongURLException();
        }
    }
}

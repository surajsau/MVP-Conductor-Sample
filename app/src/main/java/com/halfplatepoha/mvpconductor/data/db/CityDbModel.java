package com.halfplatepoha.mvpconductor.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

import static android.R.string.no;

/**
 * Created by surajkumarsau on 26/01/17.
 */

@Entity
public class CityDbModel {

    @Id
    private Long id;

    @NotNull
    private Integer cityId;

    @NotNull
    private String cityName;

    @NotNull
    private String status;

    @Generated(hash = 1997077777)
    public CityDbModel(Long id, @NotNull Integer cityId, @NotNull String cityName,
            @NotNull String status) {
        this.id = id;
        this.cityId = cityId;
        this.cityName = cityName;
        this.status = status;
    }

    @Generated(hash = 599002694)
    public CityDbModel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCityId() {
        return this.cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.zhjydy_doc.model.entity;

import android.text.TextUtils;

import static android.R.attr.value;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class ExpertInfo
{
    String id = "";
    String realname = "";
    String sex = "";
    String age = "";
    String hospital = "";
    String office = "";
    String business = "";
    String memberid = "";
    String stars = "";
    String adept = "";
    String reason = "";
    String nums = "";
    String url = "";
    String address = "";
    String recommend = "";
    String yi_cards;
    String status_z;
    String idcard = "";
    String img = "";
    String phone = "";
    String sofunc = "";

    public String getValueByKey(String key) {
        if (TextUtils.isEmpty(key))
        {
            return "";
        }
        if (key.equals("realname"))
        {
           return getRealname();
        }
        if (key.equals("sex"))
        {
            return getSex();
        }
        if (key.equals("age"))
        {
            return getAge();
        }
        if (key.equals("hospital"))
        {
            return getHospital();
        }
        if (key.equals("office"))
        {
            return getOffice();
        }
        if (key.equals("business"))
        {
            return getBusiness();
        }
        if (key.equals("memberid"))
        {
            return getMemberid();
        }
        if (key.equals("stars"))
        {
            return getStars();
        }
        if (key.equals("adept"))
        {
            return getAdept();
        }
        if (key.equals("reason"))
        {
            return getReason();
        }
        if (key.equals("nums"))
        {
            return getNums();
        }
        if (key.equals("url"))
        {
            return getUrl();
        }
        if (key.equals("address"))
        {
            return getAddress();
        }
        if (key.equals("recommend"))
        {
            return getRecommend();
        }
        if (key.equals("yi_cards"))
        {
            return getYi_cards();
        }
        if (key.equals("status_z"))
        {
            return getStatus_z();
        }
        if (key.equals("yi_cards"))
        {
            return getYi_cards();
        }
        if (key.equals("idcard"))
        {
            return getIdcard();
        }
        if (key.equals("img"))
        {
            return getImg();
        }
        if (key.equals("phone"))
        {
            return getPhone();
        }
        if (key.equals("sofunc"))
        {
            return getSofunc();
        }
        return "";
    }

    public void setValueByKey(String key, String value)
    {
        if (TextUtils.isEmpty(key))
        {
            return;
        }
        if (key.equals("realname"))
        {
            setRealname(value);
        }
        if (key.equals("sex"))
        {
            setSex(value);
        }
        if (key.equals("age"))
        {
            setAge(value);
        }
        if (key.equals("hospital"))
        {
            setHospital(value);
        }
        if (key.equals("office"))
        {
            setOffice(value);
        }
        if (key.equals("business"))
        {
            setBusiness(value);
        }
        if (key.equals("memberid"))
        {
            setMemberid(value);
        }
        if (key.equals("stars"))
        {
            setStars(value);
        }
        if (key.equals("adept"))
        {
            setAdept(value);
        }
        if (key.equals("reason"))
        {
            setReason(value);
        }
        if (key.equals("nums"))
        {
            setNums(value);
        }
        if (key.equals("url"))
        {
            setUrl(value);
        }
        if (key.equals("address"))
        {
            setAddress(value);
        }
        if (key.equals("recommend"))
        {
            setRecommend(value);
        }
        if (key.equals("yi_cards"))
        {
            setYi_cards(value);
        }
        if (key.equals("status_z"))
        {
            setStatus_z(value);
        }
        if (key.equals("yi_cards"))
        {
            setYi_cards(value);
        }
        if (key.equals("idcard"))
        {
            setIdcard(value);
        }
        if (key.equals("img"))
        {
            setImg(value);
        }
        if (key.equals("phone"))
        {
            setPhone(value);
        }
        if (key.equals("sofunc"))
        {
            setSofunc(value);
        }
    }

    public String getSofunc()
    {
        return sofunc;
    }

    public void setSofunc(String sofunc)
    {
        this.sofunc = sofunc;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRealname()
    {
        return realname;
    }

    public void setRealname(String realname)
    {
        this.realname = realname;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getHospital()
    {
        return hospital;
    }

    public void setHospital(String hospital)
    {
        this.hospital = hospital;
    }

    public String getOffice()
    {
        return office;
    }

    public void setOffice(String office)
    {
        this.office = office;
    }

    public String getBusiness()
    {
        return business;
    }

    public void setBusiness(String business)
    {
        this.business = business;
    }

    public String getMemberid()
    {
        return memberid;
    }

    public void setMemberid(String memberid)
    {
        this.memberid = memberid;
    }

    public String getStars()
    {
        return stars;
    }

    public void setStars(String stars)
    {
        this.stars = stars;
    }

    public String getAdept()
    {
        return adept;
    }

    public void setAdept(String adept)
    {
        this.adept = adept;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getNums()
    {
        return nums;
    }

    public void setNums(String nums)
    {
        this.nums = nums;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getRecommend()
    {
        return recommend;
    }

    public void setRecommend(String recommend)
    {
        this.recommend = recommend;
    }

    public String getYi_cards()
    {
        return yi_cards;
    }

    public void setYi_cards(String yi_cards)
    {
        this.yi_cards = yi_cards;
    }

    public String getStatus_z()
    {
        return status_z;
    }

    public void setStatus_z(String status_z)
    {
        this.status_z = status_z;
    }

    public String getIdcard()
    {
        return idcard;
    }

    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}

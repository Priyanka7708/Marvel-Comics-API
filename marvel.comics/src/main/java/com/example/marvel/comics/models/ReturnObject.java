package com.example.marvel.comics.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReturnObject
{
    private int code;
    private String status;
    private DataObject data;

    public int getCode()
    {
        return code;
    }

    public void setCode(int var)
    {
        this.code = var;
    }

    public DataObject getData()
    {
        return data;
    }

    public String getStatus(){return status;}

    public void setStatus(String var)
    {
        status = var;
    }

    public void setData(DataObject var)
    {
        this.data = var;
    }
}

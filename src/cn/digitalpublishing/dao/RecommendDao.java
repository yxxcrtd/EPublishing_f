package cn.digitalpublishing.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.daxtech.framework.util.CollectionsUtil;
import cn.digitalpublishing.ep.po.Recommend;

public class RecommendDao  extends CommonDao<Recommend,String>{
	public List<Recommend> getRecommendList(Map<String, Object> condition, String sort, Integer pageCount, Integer page) throws Exception {
		List<Recommend> list=null;
		
		String hql=" from Recommend a left join a.publications b";
		Map<String,Object> t=this.getWhere(condition);
		String property=" id,publications.id,recommendType,createDate,publications.id,publications.code,publications.available,publications.title";
		String field="a.id,a.publications.id,a.recommendType,a.createDate,b.id,b.code,b.available,b.title";
		try{
			System.out.println((List<Object>)t.get("condition"));
			list=super.hibernateDao.getListByHql(property,field, hql+t.get("where").toString(), ((List<Object>)t.get("condition")).toArray(),sort, Recommend.class.getName());
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	private Map<String, Object> getWhere(Map<String,Object> map) {
		// TODO Auto-generated method stub
		Map<String,Object> table=new HashMap<String,Object>();
		String whereString="";
		List<Object> condition=new ArrayList<Object>();
		int flag=0;	
		if(CollectionsUtil.exist(map, "recommendType")&&!"".equals(map.get("recommendType"))){
			if(flag==0){
				whereString+=" where a.recommendType = ?";
				flag=1;
			}else{
				whereString+=" and a.recommendType = ?";
			}
			condition.add(map.get("recommendType"));
		}
		whereString+="order by RECOMMEND_ORDER";
		table.put("where",whereString);
		table.put("condition", condition);
		return table;
	}
}

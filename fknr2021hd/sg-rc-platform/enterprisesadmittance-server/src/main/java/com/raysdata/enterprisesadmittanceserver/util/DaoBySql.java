package com.raysdata.enterprisesadmittanceserver.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;


public  class DaoBySql {
	
    private JdbcTemplate jdbcTemplate;

    public static List mapToEntyList(List list, Class cls)
    {
    	List all = new ArrayList();
    	try {
    		//取所有的列
    		List listKey = new ArrayList();
    		if (list!=null && list.size()!=0)
    		{
    			Map map = (Map) list.get(0);
    			Iterator it = map.keySet().iterator();
    			while(it.hasNext())
    			{
    				listKey.add(it.next());
    			}
    			//取所有对应的真正方法
    			Method[] funArray = cls.getDeclaredMethods();
    			Map funMap = new HashMap();
    			for (int i = 0; i < funArray.length; i++)
    			{
    				funMap.put(funArray[i].getName().toUpperCase(), funArray[i]);
    			}
    			for (int i = 0; i < list.size(); i++)
    			{
    				//造对象
        			Object object = cls.newInstance();
    				Map tempMap = (Map) list.get(i);
    				for (int j = 0; j < listKey.size(); j++)
    				{
    					String columFun = "SET"+listKey.get(j).toString().toUpperCase();
    					//if (listKey.get(j).toString().equals("TN")) continue;
    					//取取值和取取方法名
    					Object objTemp = tempMap.get(listKey.get(j));
    					Method m = (Method) funMap.get(columFun);
    					Class[] c = m.getParameterTypes();
    					if (objTemp instanceof BigDecimal)
    					{
    						if (c[0].getName().equals("java.lang.Integer"))
    						{
    							objTemp = ((BigDecimal)objTemp).intValue();
    						}
    						else if(c[0].getName().equals("java.lang.Long"))
    						{
    							objTemp = ((BigDecimal)objTemp).longValue();
    						}
    					}
    					m.invoke(object, objTemp);
    				}
    				all.add(object);
    			}
    			System.out.println();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return all;
    }
    
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
   

}

package com.mywebflux.helper;

import com.mywebflux.model.Customer;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestHelper {

    public List<Customer> getCustomerDetails() {
        List<Customer> customerList = new ArrayList<Customer>();
        BufferedReader br = null;
        try {
            InputStream inputFS = getClass().getClassLoader().getResourceAsStream("data/us-500.csv");
            br = new BufferedReader(new InputStreamReader(inputFS));
            customerList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch (Exception ee) {}
        }
        return customerList;
    };
    public List<Customer> getSomeCustomerDetails(int nos) {
        List<Customer> customerList = new ArrayList<Customer>();
        BufferedReader br = null;
        try {
            InputStream inputFS = getClass().getClassLoader().getResourceAsStream("data/us-500.csv");
            br = new BufferedReader(new InputStreamReader(inputFS));
            customerList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            if(nos>0) {
                return customerList.subList(0, nos);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch (Exception ee) {}
        }
        return customerList;
    };
    private Function<String, Customer> mapToItem = (line) -> {
        try {
            String s = ", ";
            List kk = List.of(line.replace(s, "#").split(","));
            List newList= (List) kk.stream().map(x -> x.toString().replaceAll("#",", ")).collect(Collectors.toList());
            Customer item = new Customer();
            item.setFirst_name((String) newList.get(0));
            item.setLast_name((String) newList.get(1));
            item.setCompany_name((String) newList.get(2));
            item.setAddress((String) newList.get(3));
            item.setCity((String) newList.get(4));
            item.setCountry((String) newList.get(5));
            item.setState((String) newList.get(6));
            item.setZip((String) newList.get(7));
            item.setPhone1((String) newList.get(8));
            item.setPhone2((String) newList.get(9));
            item.setEmail((String) newList.get(10));
            item.setWeb((String) newList.get(11));
            List<String> lst = new ArrayList();
            if(!StringUtils.isEmpty(item.getPhone1())) {
                lst.add(item.getPhone1());
            }
            if(!StringUtils.isEmpty(item.getPhone2())) {
                lst.add(item.getPhone2());
            }
            if(!lst.isEmpty()) {
                item.setPhoenNos(lst);
            }
            return item;
        } catch (Exception ex) {
            System.out.println("Line="+line);
            ex.printStackTrace();
            throw ex;
        }
    };
}

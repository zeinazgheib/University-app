package com.uapp.util;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.uapp.adapter.BranchInfo;
import com.uapp.adapter.DepartmentInfo;

public class Globals {
	public static String username;
	public static String password;
	public static Vector<BranchInfo> branches = new Vector<BranchInfo>();
	public static Vector<DepartmentInfo> departments = new Vector<DepartmentInfo>();
	public static int branchId;
	public static int depId;
	public static List<Map<String, String>> feedList;
}

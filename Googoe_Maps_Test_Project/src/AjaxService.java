


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.util.ArrayList;




/**
 * Servlet implementation class AjaxService
 */
@WebServlet("/AjaxService")
public class AjaxService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		Connection con;
		PreparedStatement pst;
		
		System.out.println("hit get");
		
		try {
			
			//System.out.println(request.getParameter("test"));
			String json = request.getParameter("markerInfo");
			
			// Get Gson object
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			// parse json string to object
			Marker marker = gson.fromJson(json, Marker.class);
			
			// print object data
			System.out.println(marker.getLat());
		    
			// connecting to mysql to store marker data
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection ("jdbc:mysql://localhost/googlemaps", "root", "");
			System.out.println("Successfully Connected to the Database");
			pst = (PreparedStatement) con.prepareStatement("INSERT INTO marker (lat, lng, description) VALUES (?, ?, ?)");
			pst.setDouble(1, marker.getLat());
			pst.setDouble(2, marker.getLng());
			pst.setString(3, marker.getDesc());
			pst.executeUpdate();
			System.out.println("Marker saved in database");
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		Connection con;
		ArrayList<Marker> markerData = new ArrayList<Marker>();
		
		System.out.println("hit post");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection ("jdbc:mysql://localhost/googlemaps", "root", "");
			System.out.println("Successfully Connected to the Database");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT lat, lng, description FROM googlemaps.marker");
			
			while(rs.next()){
				Marker marker = new Marker();
				
				double lat = rs.getDouble("lat");
				double lng = rs.getDouble("lng");
				String desc = rs.getString("description");
				
				marker.setLat(lat);
				marker.setLng(lng);
				marker.setDesc(desc);
				
				markerData.add(marker);
			}
			rs.close();
			
			String json = new Gson().toJson(markerData);
            response.setContentType("application/json");
            response.getWriter().write(json);
            
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}

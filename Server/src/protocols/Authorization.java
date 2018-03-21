package protocols;


import lowentry.ue4.libs.jackson.databind.JsonNode;
import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import other.Encode;


public class Authorization
{
	public static HashMap<String, Object> Handle(JsonNode dataNode)
	{
		HashMap<String,Object> response = new HashMap<String,Object>();
		response.put("Action", "Authorization");
		HashMap<String,Object> data = new HashMap<String,Object>();

		if(dataNode != null)
		{
			JsonNode dataLoginNode = dataNode.get("Login");
			JsonNode dataPasswordNode = dataNode.get("Password");

			if(dataLoginNode != null && dataPasswordNode != null)
			{
				String dataLogin = dataLoginNode.textValue();
				String dataPassword = dataPasswordNode.textValue();

				try
				{
					String HashPassword = Encode.HashPassword(dataPassword);
					ResultSet rs = Database.SendSelectQuery("SELECT login, banned FROM accounts WHERE login='" + dataLogin + "' AND password='" + HashPassword + "'");

					if(rs.next() == true)
					{
						if(rs.getInt("banned") == 0)
						{
							System.out.println("Auth User: " + dataLogin);
							data.put("Status", 0);
						}
						else if(rs.getInt("banned") == 1)
							data.put("Status", 3);
					}
					else
						data.put("Status", 2);
				}
				catch(SQLException e)
				{
					e.printStackTrace();
					data.put("Status", 1);
				}
			}
			else
				data.put("Status", 1);
		}
		else
			data.put("Status", 1);

		Database.Closed();
		response.put("Data", data);
		return response;
	}
}

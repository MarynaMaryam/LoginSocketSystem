package protocols;


import database.Database;
import lowentry.ue4.libs.jackson.databind.JsonNode;
import other.Encode;

import java.sql.SQLException;
import java.util.HashMap;


public class Registration
{
	public static HashMap<String, Object> Handle(JsonNode dataNode)
	{
		HashMap<String,Object> response = new HashMap<String,Object>();
		response.put("Action", "Registration");
		HashMap<String,Object> data = new HashMap<String,Object>();

		if(dataNode != null)
		{
			JsonNode dataLoginNode = dataNode.get("Login");
			JsonNode dataPasswordNode = dataNode.get("Password");

			if(dataLoginNode != null && dataPasswordNode != null)
			{
				String dataLogin = dataLoginNode.textValue();
				String dataPassword = dataPasswordNode.textValue();

				if(dataLogin.length() >= 4 && dataPassword.length() >= 4)
				{
					try
					{
						if(Database.SendSelectQuery("SELECT login FROM accounts WHERE login='" + dataLogin + "'").next() == false)
						{
							String HashPassword = Encode.HashPassword(dataPassword);
							Database.SendUpdateQuery("INSERT INTO accounts (login, password) VALUES ('" + dataLogin + "', '" + HashPassword + "')");
							System.out.println("Add User: Login - " + dataLogin + ", Password - " + HashPassword);
							data.put("Status", 0);
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
					data.put("Status", 3);
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

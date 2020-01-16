package com.peru21.backend.apirest.auth;

public class JWTConfig {

	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345678"; 
	
	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpQIBAAKCAQEAu2lKKzbQo49z2C0+h6c1WZ2ephCGO6k4Kd1W3UmsbmgUEmwM\r\n" + 
			"ITdVfb3RKdzeoqb7pZnOaC85XQvP474hDqkPlQx762LZe8odltdu1eJrsiL494ZM\r\n" + 
			"0o4Nnuj80MYUVgfPOAyWDuLb2GH5kWqXPg77vU6hB4MqRYU3wk3IE+xTZPTzDqoG\r\n" + 
			"yQyVd1YBb10b1QY5Dt+izGV8OdfXiybuEDS27GWOflNff4o5wvVliP3EjtqwqkJL\r\n" + 
			"TPKkva9B9vfZ4w0P0BhvbdsJk+pDUrNuZGJQMHsmzZyATdN/iZcPcwAdHZcbhSgt\r\n" + 
			"VI2N59xAZOk8u7tAkGYdEeitzY2NIxH9+4QZ6QIDAQABAoIBAA+dzW1eavJGINwR\r\n" + 
			"oRVCrKJkDPU/iT/tcN2kMPnNis3EZG/IixY1ey04JPa9Ddw11+BLubRawwfq1Q7g\r\n" + 
			"J/cFxWOhH65UlVTIWcDZAMZX0KuOWHqfuUUOSzpDpdVrvAQTMIQ2IEbrRnB4cYhZ\r\n" + 
			"fnLQYs+6fpaWqm4SlLvfKV3nomdWERz6dcXAt5w5fRzC+VuHuE+1geA1H5MbXJMk\r\n" + 
			"deiPo+cUDIV0Jh49gYjpu+VuHzKQfNDsjS846rRtWYuk8VzLQJWkA9XTSPR33/5P\r\n" + 
			"nnmPoa5zzrk3llTi4a0yzSbL2XOVrAiUcis73Pz371xpcmZNgMBLzlEzns0amL6f\r\n" + 
			"48uOJtECgYEA+c5PMaSgADr+Sd8ss3qc8fhsuLTiOObuv5ocRYjp8OCxthPyeeD4\r\n" + 
			"xNLzEPXW0mWEeTm+ue2/Z2M5vvN1TxQ4pse3xN+EE8JmKArDQYi2p1eDpDCvIIWS\r\n" + 
			"qkt7MD0rhYqSUoqkg5FHT4GRTUKN/QL8+5kBQB9Aq9YFVzw2/NFNKc0CgYEAwA7r\r\n" + 
			"LmNDsD12jACBIC87rEbh2M6qIouivqmZHiTepsqXaMaaS6d+G1WUsSahCIlzf1Sy\r\n" + 
			"uM6Hw4xtozgVgQXV5IOXZbMuF9ECI7XWiwb7Dy2O9t5yq0fARHrjFh+EO1heYvXl\r\n" + 
			"1k/QDeON1Dm162ZuJxFWlV1ybRiD86cqZV5wZI0CgYEA00cHuf/3h1pd2hLUltQc\r\n" + 
			"S5cGNU5fiyreQNVSFgmPezg/dbT3Ptfk72tPQ0f5sidEXNdXFaAcY5ALthHpZMHA\r\n" + 
			"jGacafl1ltSzXCiGLjY37d7ZqxO1+raebkO64/jIh4DE8uTp8ZD9FCSMxSCGQLi3\r\n" + 
			"SQCGizojk3JHQBnglAFd1v0CgYEAjOQk2RHO3SIprgjMsRyVTQtusMri+pavG+l0\r\n" + 
			"vy1S/M6QBjfzht5nX4wHmXF6HrYeuWEb/wDbjbOd1mfVNqJeR9XVIbExdh1I0YOa\r\n" + 
			"0MCdOoQ0ZZvISShIvrPblSYnrhVkoHo9UluYiCfPqslvUDIDSruoZch4odmjKwIN\r\n" + 
			"0vFVyB0CgYEAsTUCl/3WXlc1Y0GxNNj7OG5coPxLU/p8p+IAhENaeroTGRicAzUh\r\n" + 
			"dE4MCG51walhO+R7mITBBKObbZCPTRegbouScLWRaXwZVosmFXu4tjw9a8nor3Vk\r\n" + 
			"VNTioTz5DOI0g686fT28mf3Kw4RbrU4y6UCxOANt64lC8CGV1MY76fI=\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAu2lKKzbQo49z2C0+h6c1\r\n" + 
			"WZ2ephCGO6k4Kd1W3UmsbmgUEmwMITdVfb3RKdzeoqb7pZnOaC85XQvP474hDqkP\r\n" + 
			"lQx762LZe8odltdu1eJrsiL494ZM0o4Nnuj80MYUVgfPOAyWDuLb2GH5kWqXPg77\r\n" + 
			"vU6hB4MqRYU3wk3IE+xTZPTzDqoGyQyVd1YBb10b1QY5Dt+izGV8OdfXiybuEDS2\r\n" + 
			"7GWOflNff4o5wvVliP3EjtqwqkJLTPKkva9B9vfZ4w0P0BhvbdsJk+pDUrNuZGJQ\r\n" + 
			"MHsmzZyATdN/iZcPcwAdHZcbhSgtVI2N59xAZOk8u7tAkGYdEeitzY2NIxH9+4QZ\r\n" + 
			"6QIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
	
	
}

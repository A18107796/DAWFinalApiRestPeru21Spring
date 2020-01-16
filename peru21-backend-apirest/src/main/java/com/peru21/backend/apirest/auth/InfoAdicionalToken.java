 package com.peru21.backend.apirest.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.peru21.backend.apirest.entity.Empleado;
import com.peru21.backend.apirest.entity.Usuario;
import com.peru21.backend.apirest.services.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		Usuario usuario = usuarioService.findByUsername(authentication.getName()); 
		Map<String, Object> info = new HashMap<>();
		
		if(usuario.getEstudiante() == null) {
			info.put("info_adicional",  authentication.getName());
			info.put("nombre usuario",  usuario.getUsername()); 
			info.put("id",   usuario.getEmpleado().getId());
			info.put("emp",   usuario.getEmpleado().getNombre());
			info.put("foto",   usuario.getEmpleado().getFoto());
		}
		
		if(usuario.getEmpleado() == null) {
			info.put("info_adicional",  authentication.getName());
			info.put("nombre usuario",  usuario.getUsername()); 
			info.put("id_es",   usuario.getEstudiante().getId());
			info.put("est",   usuario.getEstudiante().getNombres());
		}
		

		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}
	
}

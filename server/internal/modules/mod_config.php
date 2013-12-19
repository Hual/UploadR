<?php

if(defined('CUSTOM_LINK_CONFIG'))
	require_once CUSTOM_LINK_CONFIG;
else 
	require_once "config.php";

class Configuration
{
	public static function get($setting)
	{
		global $gSettings;
		return $gSettings[$setting];
	}
}

?>
<?php

define("UPLOADR_SUCCESS", "S");
define("UPLOADR_ERROR", "E");

class Output
{
	public static function success($info)
	{
		die(UPLOADR_SUCCESS.$info);
	}

	public static function error($info)
	{
		die(UPLOADR_ERROR.$info);
	}
}

?>

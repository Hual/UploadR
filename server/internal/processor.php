<?php

require_once "modules/mod_input.php";
require_once "modules/mod_config.php";
require_once "modules/mod_key.php";
require_once "modules/mod_upload.php";
require_once "modules/mod_output.php";

header('Cache-Control: no-cache, no-store, must-revalidate');
header('Expires: 0');

$inputHandler = new InputHandler();

if(!$inputHandler->isUserAgentValid()) Output::error("Unknown client");

if($inputHandler->hasRequiredLength() && $inputHandler->hasValidHeader())
{
	switch($inputHandler->getHeader())
	{
		case 'u':
		{
			if(!$inputHandler->hasRequiredUploadLength()) Output::error("wowe");

			if(KeyHandler::isKeyRequired())
			{
				if(!$inputHandler->hasKey() || !$inputHandler->isKeyValid()) Output::error("Key required");
				if(!KeyHandler::keyExists($inputHandler->getKey())) Output::error("Invalid key");
			}

			$uploader = new Uploader($inputHandler->getType(), $inputHandler->getData());

			if($uploader->getSize() > 10485760 || $uploader->getSize() < 16) Output::error("Size outside boundaries");
			if($uploader->getType() == NULL) Output::error("Unknown type");

			Output::success($uploader->save($uploader->getType()));
			break;
		}
		case 'k':
		{
			if(KeyHandler::isKeyRequired()) Output::success("");
			break;
		}
		case 'i':
		{
			Output::success("");
			break;
		}
	}
}
	
?>
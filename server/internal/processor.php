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
			if(KeyHandler::isKeyRequired())
			{
				if(!$inputHandler->hasKey() || !$inputHandler->isKeyValid()) Output::error("Key required");
				if(!KeyHandler::keyExists($inputHandler->getKey())) Output::error("Invalid key");
			}

			$uploader = new Uploader($inputHandler->getImageType(), $inputHandler->getImageData());

			if($uploader->getImageSize() > 10485760) Output::error("Image size too big");
			if($uploader->getImageType() == NULL) Output::error("Unknown image type");

			Output::success($uploader->saveImage($uploader->getImageType()));
			break;
		}
		case 'k':
		{
			Output::success((int)KeyHandler::isKeyRequired());
			break;
		}
		case 'i':
		{
			Output::success(1);
			break;
		}
	}
}
	
?>
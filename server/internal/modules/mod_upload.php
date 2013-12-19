<?php

class Uploader
{
	private $imageType;
	private $imageData;

	function __construct($type, $data)
	{
		$this->imageType = $type;
		$this->imageData = $data;
	}

	public function getImageSize()
	{
		return sizeof($this->imageData);
	}

	public function getImageType()
	{
		switch($this->imageType)
		{
			case 'p':
			{
				return "png";
			}
			case 'j':
			{
				return "jpg";
			}
			default:
			{
				return NULL;
			}
		}
	}

	public function saveImage($type)
	{
		do
		{
			$name = substr(str_shuffle(uniqid("", true)."abcefghijklmnopqrstuvwxyz".uniqid("", true)."ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".uniqid("", true)), 0, Configuration::get("name-length"));
		}
		while(file_exists("../".$name.".".$type));
		file_put_contents("../".$name.".".$type, $this->imageData);
		return $name.".".$type;
	}
}

?>

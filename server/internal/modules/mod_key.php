<?php

class KeyHandler
{
	private $keyString;
	private $fPath;

	function __construct($path)
	{
		$this->fPath = $path;
		$this->keyString = str_replace("*/ ?>", "", str_replace("<?php /*", "", file_get_contents($this->fPath)));
	}

	public function getKeys()
	{
		return $this->keyString;
	}

	public function addKey($key)
	{
		$this->keyString .= $key."\n";
	}

	public function updateKeys()
	{
		file_put_contents($this->fPath, "<?php /*".$this->keyString."*/ ?>");
	}

	public function _keyExists($key)
	{
		return !(strpos($this->keyString, $key) === false);
	}

	public static function isKeyRequired()
	{
		return (Configuration::get("require-key"));
	}

	public static function keyExists($key, $path="keys.php")
	{
		return !(strpos(file_get_contents($path), $key) === false);
	}
}
	
?>
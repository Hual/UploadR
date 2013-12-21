<?php
	session_start();
	generate_captcha();

	function generate_captcha()
	{
		$_SESSION['captcha'] = substr(str_shuffle("abcefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"), 0, rand(4, 8));
		$image = imagecreatetruecolor(220, 80);
		ImageFill($image, 0, 0, ImageColorAllocate($image, 255, 255, 255));
		$rot = rand(-15.0, 15.0);
		imagettftext($image, rand(17.0, 21.0), $rot, rand(5.0, 30.0), 50-($rot*-1.4), rand_col($image), "./resources/".rand(0, 6).".ttf", $_SESSION['captcha']);
		$lines = rand(1, 4);
		for($i=0;$i<$lines;++$i)
			imageline($image, rand(0, 220), rand(0, 80), rand(0, 220), rand(0, 80), rand_col($image));
		header("Content-Type: image/jpeg");
		imagepng($image);
	}

	function rand_col($img)
	{
		return ImageColorAllocate($img, rand(0, 150), rand(0, 150), rand(0, 150));	
	}
?>
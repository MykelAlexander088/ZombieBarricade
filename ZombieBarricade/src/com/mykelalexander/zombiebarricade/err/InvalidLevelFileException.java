package com.mykelalexander.zombiebarricade.err;

public class InvalidLevelFileException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public InvalidLevelFileException(){
		super("THIS FILE IS NOT A VALID LEVEL FILE");
	}

}

package com.edutrack.framework;

/**
 *  클래스의 해제를 담당하는 인터페이스 정의    
 */		
public interface Disposable 
{
	
	/**
	 * 클래스 초기화 및 메모리 해제 메소드
	 */
	public void dispose();
}

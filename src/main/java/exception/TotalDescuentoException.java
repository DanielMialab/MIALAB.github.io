/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

public class TotalDescuentoException
  extends Exception
{
  private static final long serialVersionUID = 6933150456422042905L;
  
  public TotalDescuentoException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  public TotalDescuentoException(String message)
  {
    super(message);
  }
  
  public TotalDescuentoException(Throwable cause)
  {
    super(cause);
  }
}
package dk.mtdm;

public class QuadStore {
  public float x, effectNum, extra, y;
  public QuadStore(float x, float effectNum, float extra, float y){
    this.x = x;
    this.effectNum = effectNum;
    this.extra = extra;
    this.y = y;
  }
  public QuadStore(){
    this.x = 0;
    this.effectNum = 0;
    this.extra = 0;
    this.y = 0;
  }
  public QuadStore setX(float x){
    this.x = x;
    return new QuadStore(x, effectNum, extra, y);
  }
  public QuadStore setNum(float effectNum){
    this.effectNum = effectNum;
    return new QuadStore(x, effectNum, extra, y);
  }
  public QuadStore setExtra(float extra){
    this.extra = extra;
    return new QuadStore(x, effectNum, extra, y);
  }
  public QuadStore setY(float y){
    this.y = y;
    return new QuadStore(x, effectNum, extra, y);
  }
}

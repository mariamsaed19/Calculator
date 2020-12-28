<template>
  <div id="app" class="calc">
    <div id="disp">
      <div id="exp">{{ exp }}</div>
      <div id="res"> {{res}} </div>
      </div>
    <div class="nums">
      <button v-on:click="addNum('1')" id="1" class="button">1</button>
      <button v-on:click="addNum('2')" id="2" class="button">2</button>
      <button v-on:click="addNum('3')" id="3" class="button">3</button>
      <button v-on:click="addNum('4')" id="4" class="button">4</button>
      <button v-on:click="addNum('5')" id="5" class="button">5</button>
      <button v-on:click="addNum('6')" id="6" class="button">6</button>
      <button v-on:click="addNum('7')" id="7" class="button">7</button>
      <button v-on:click="addNum('8')" id="8" class="button">8</button>
      <button v-on:click="addNum('9')" id="9" class="button">9</button>
      <button v-on:click="addSign()" id="sign" class="button">&#177;</button>
      <button v-on:click="addNum('0')" id="0" class="button">0</button>
      <button v-on:click="addPoint()" id="point" class="button">.</button>
    </div>

    <div class="op1">
      <button v-on:click="addOp('%')" id="percent" class="button3"> &#37; </button>
      <button v-on:click="clear" id="CE" class="button3"> CE </button>
      <button v-on:click="clear" id="C" class="button3"> C </button>
      <button v-on:click="del" id="back" class="button3"> 	&#x2421; </button>
      <button v-on:click="addOp('in')" id="inverse" class="button3"> &#8543;x </button>
      <button v-on:click="addOp('sq')" id="sq" class="button3"> x&#178; </button>
      <button v-on:click="addOp('sqr')" id="sqr" class="button3"> &#8730; x </button>
      <button v-on:click="addOp('/')" id="division" class="button3"> &#247; </button>
    </div>

    <div class="op2">
      <button v-on:click="addOp('*')" id="multi" class="button2"> x </button>
      <button v-on:click="addOp('+')" id="sum" class="button2"> + </button>
      <button v-on:click="addOp('-')" id="sub" class="button2"> &#8722; </button>
      <button v-on:click="result(0)" id="equal" class="button2"> = </button>
    </div>


  </div>

</template>

<script>
import axios from 'axios'
export default {

  data() {
    return ({
      res: '', //shown number
      exp: '',  // the whole expression
      first: '0',  // the result of whole expression
      second: '',  // the shown number
      point: false,  // decimal point flag
      op: '',       // operation
      o: false,     //operation flag
      s2: false,     //sign flag of shown number
      flag:"",       //for equal 0  add another operation 1
      flag2: false   //flag for sq/sqr/inverse operations
    })
  },

  methods: {
    result(flag) {
      if(this.second=="0."){
        this.second="0"
      }
      var oper=this.exp.charAt(this.exp.length-1)
      this.flag=flag
      var v = this;
      //change
      if(flag==0&&this.second==""&&(oper=="+")){
        this.second=this.first
      }
      if (this.second != "") {

        console.log("first :  " + this.first + " second : " + this.second + " op: " + this.op)
        var temp = v.second
        axios.get("http://localhost:8085/op", {
          params: {
            num1: v.first,
            num2: v.second,
            op: v.op
          }
        })
            .then(function (response) {

              if (response.data.length != "") {
                v.first = response.data
                v.res = response.data
              } else {
                v.first = temp
                v.res = temp
              }
            })
      }
      console.log("result :  " + this.res)

      if (flag == 0 && this.second != "0") {

        if (this.s2 == true) {
          this.exp = this.exp + "(" + this.second + ")"
        } else {
          this.exp = this.exp + this.second
        }
        this.second = ""
        this.point = false
        this.s2 = false
      }



  },
  addNum: function (Num) {

    if ((this.o == false && this.second == "") || this.res == "E") {
      this.clear()
    }
    if (this.second == "") {
      this.o = false
    }
    if (this.second == "0") {
      this.second = Num
    } else {
      this.second = this.second + Num
    }

    this.res = this.second
  },
  addOp: function (op) {
    if (this.res == "E") {
      this.clear()
    }
    if (op == "sq" || op == "sqr" || op == "in" || op == "%") {
      var v = this;
      this.flag2 = false
      if (this.second == ""&&this.op!="") {
        this.second = this.first

        if(this.flag=="0") {
          this.flag2 = true
          this.first = "0"
        }

      }
      var sn=this.first
      if(op=="%"&&(this.op=="/"||this.op=="*")){
        sn="1"
      }
      axios.get("http://localhost:8085/op", {
        params: {
          num1: v.second,
          num2: sn,
          op: op
        }
      })
          .then(function (response) {
            if (v.flag2 == true) {
              v.clear()
            }
            v.second = response.data
            v.res = v.second


          })

    } else {
      if (this.o == false) {

        this.o = true
        this.result(1)
        this.op = op
        if(this.res!="") {
          if (this.s2 == true) {
            this.exp = this.exp + "(" + this.second + ")" + this.op
          } else {
            this.exp = this.exp + this.second + this.op
          }
        }
        else{
          this.op=""
          this.o=false
        }
        this.second = ""
        this.point = false
        this.s2 = false
      }
      else {
        console.log("p here")
        var l = this.exp.length
        this.op = op
        this.exp = this.exp.slice(0, l ) + this.op
      }
    }
  },
  clear: function () {
    this.res = '',
        this.exp = '',
        this.first = '0',
        this.second = '',
        this.op = '',
        this.o = false,
        this.point = false,
        this.s2 = false
  },
  del: function () {
    if (this.second.length == 1 || this.second == "0"||(this.second.charAt(0)=="-"&&this.second.length==2)) {
      this.second = "0"
      this.res = "0"
    } else {
      this.second = this.second.slice(0, this.second.length - 1)
      this.res = this.second
    }
    console.log(this.second)
  },
  addPoint() {
      if(this.flag=="0"){
        this.clear()
      }
    if (this.point == false) {
      this.point = true
      if (this.second == "") {
        this.second = "0."
      } else {
        this.second = this.second + "."
      }
      this.res = this.second
    }
  },
  addSign() {
    if(this.flag=="0"){
      var k=this.res
      this.clear()
      this.second=k
      this.res=k
      if((k * 1)<0){
        this.s2=true
      }
    }
    if (this.second != "0" && this.second != "") {
      console.log("Start :  " + this.second)
      if (this.s2 == false) {  //positive to negative 5 -> -5
        this.second = "-" + this.second
      } else {    //negative to positive   -5   -> 5
        this.second = this.second.slice(1, this.second.length)
      }
      this.res = this.second
      this.s2 = !this.s2
      console.log("end :  " + this.second)
    }
  }

}
  }

</script>

<style>
body{
  background-color: rgb(84, 88, 88);
}
.calc{
  border: black;
  border-style: ridge;
  position: absolute;
  display: grid;
  justify-content: center;
  background-color: rgb(15, 32, 32);
  width: 600px;
  height: 600px;
  left: 450px;
  top: 70px;
  padding: 10px;
  grid-template-columns: repeat(4,150px) ;
  grid-template-rows: 150px 100px 350px;
  grid-template-areas:
    "res res res res"
    "op1 op1 op1 op1"
    "num num num op2"

;
}
#disp,.op1,.op2,.nums{
  display: grid;
}
#exp{
overflow: auto;
  margin-left: 30px;
  margin-right: 30px;
  margin-top: 25px;
  font-size: large;
}
#res{
  overflow: auto;
  margin-left: 30px;
  margin-right: 30px;
  margin-top: 5px ;
}
#disp{
  width: 600px;
  height: 150px;
  background-color: rgb(211, 206, 203);
  justify-content: left;
  font-size: x-large;
  font-weight: bolder;
  border-radius:80px;
  grid-area: res;
}
.op1{

  grid-area: op1;
  grid-template-columns: repeat(4,150px) ;
  grid-template-rows: 50px 50px;
}
.op2{
  grid-area: op2;
  grid-template-columns: 150px;

}
.nums{
  grid-area: num;
  grid-template-columns: repeat(3,150px) ;

}
.button,.button2,.button3 {
  background-color: gainsboro;
  border-radius: 50%;
  border: 4px solid rgb(177, 142, 231);
  margin: 5px;
  display: flex;
  justify-content: center;
  padding-top: 20px;
  font-size: xx-large;
  font-weight: bolder;
}
.button2,.button3{
  border-radius: 12px;
}
.button3{
  padding-top: 5px;
  font-size: larger;
}
.button:hover,.button2:hover,.button3:hover{
  background-color: rgb(203, 202, 248);
}
.button:active,.button2:active,.button3:active{
  background-color: #c1b1ec;
  box-shadow: 0 5px rgb(102, 101, 117);
  transform: translateY(7px);
}
</style>
//this is a comment
//comment
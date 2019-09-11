<template>
  <header class="v-header container" >
      <div @click="vdclick" class="fullscreen-video-wrap">
          <video playsinline autoplay muted loop>
           <source :src='video' type='video/mp4'>
          </video>
          <div class="layer"></div>
      </div>
      <div class="header-overlay">
      </div>
      <div class="header-content text-center; " style="margin-left: 150px;">
        <h1 class="s3" style="color: white;">여행 그 시작은</h1>
        <h1 class="s3 border" style="letter-spacing: 3px; color: white;">&nbsp;&nbsp;&nbsp;&nbsp;준비하는 <span class="s3" id="sul">설렘</span></h1>
        <p style="color: white; margin-top:10px; margin-left: 45px;"> <span id="sul">&nbsp;&nbsp;&nbsp;&nbsp;설렘</span> 과 함께 떠나는 여행 </p>
      </div>
    </header>
</template>

<script>
import router from '@/router'
import { setTimeout } from 'timers'

export default {
  mounted: function () {
    this.$store.state.val = false
    console.log(this.video)
    console.log(this.$route['name'])
  },
  data () {
    return {
      video: `vid${Math.ceil(Math.random() * 4)}_Trim.mp4` // 랜덤비디오 개수
    }
  },
  methods: {
    vdclick: function () {
      var element = document.getElementsByClassName('fullscreen-video-wrap')[0]
      var elementt = document.getElementsByClassName('header-content')[0]
      element.classList.add('animate_content')
      elementt.classList.add('animate_content')
      setTimeout(function () {
        console.log('Works!')
        router.push({ path: 'home' })
      }, 1500)
    }
  }
}
</script>

<style>
*{
  box-sizing: border-box;
}

@media (max-width: 600px) {
  .header-content {
    margin: auto !important;
  }

  .header-content h1{
    font-size:30px !important;
  /* text-align: left !important; */
  }

  .header-content p{
    font-size:20px !important;
    margin: auto !important;
    margin-top: 6px !important;
  }
  h1.border:after {
    content: "";
    display: block;
    width: 242px !important;
    border-bottom: 5px solid #00BFA5;
    margin-left: 10px;
  }
}

body{
  margin:0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-size:1rem;
  font-weight:normal;
  line-height:1.5;
  overflow-x:hidden;
}

.v-header{
  height:100vh;
  display:flex;
  align-items:center;
  color: black;
}

.fullscreen-video-wrap{
  position:absolute;
  top:0;
  left:0;
  width:100vw;
  height:100vh;
  z-index: 2;
  overflow:hidden;
}

.fullscreen-video-wrap video{
  min-height:100%;
  min-width:100%;
}

.header-overlay{
  height:100vh;
  position: absolute;
  top:0;
  left:0;
  width:100vw;
  z-index:1;
  /* background:#225470; */
  opacity:0.85;
}

.header-content{
  z-index:2;
}

.header-content h1{
  font-size:50px;
  margin-bottom:0;
}

.header-content p{
  font-size:1.5rem;
  display:block;
  padding-bottom:2rem;
}

#sul {
  color: #00BFA5;
}

/* 글씨에 그림자 효과 */
.s1 { text-shadow: 2px 2px 2px gray; }
.s2 { text-shadow: 4px 2px 2px gray; }
.s3 { text-shadow: 5px 2px 2px gray; }

/* 밑줄긋기 */
h1.border:after {
    content: "";
    display: block;
    width: 390px;
    border-bottom: 5px solid #00BFA5;
    margin-left: 10px;
}

/* 배경영상에 반투명한 레이어 씌우기 */
.layer {
    background-color: rgba(67, 78, 80, 0.2);
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
}

.animate_content {
  animation: animate 3s ease;
}

@keyframes animate {
  10% {
    transform: scale(1, 0.002);
  }
  35% {
    transform: scale(0.2, 0.002);
    opacity: 1;
  }
  50% {
    transform: scale(0.2, 0.002);
    opacity: 0;
  }
  85% {
    transform: scale(1, 0.002);
    opacity: 1;
  }
  100% {
    transform: scale(1, 1);
  }
}
</style>

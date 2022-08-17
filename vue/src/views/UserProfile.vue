<template>
  <div id="user-profile">
    <h1 id="profile-header">
      <Header></Header>
    </h1>
    <div class="row  py-5 px-4">
      <div class="col-md-5 mx-auto ">
        <!-- Profile -->
        <div class="bg-white shadow rounded">
          <div class="px-4 pt-0 pb-1 cover ">
            <div class="media align-items-end profile-head ">
              <div id="profile-pic" class="profile mr-3" v-if="item.imageUrl">
                <img :src="item.imageUrl" />
              </div>

              <div class="mb-4" id="profile-pic"  v-else>
                <img
                  class="rounded-circle mt-0"
                  src="https://ca.slack-edge.com/T0GNFLF6D-U03DK8YV9LZ-3d43d76e3219-512"
                />
              </div>
            </div>     
        </div>
              
            
              <div class="media-body mb-1  text-black">
                <h4 class="mt-5 mb-0">
                  {{ user.firstName }} {{ user.lastName }}
                </h4>
                <p class="small mb-4">Username : {{ user.username }}</p>
                <p class="small mb-4">Email : {{ user.email }}</p>
              </div>
              <label
                id="upload-label"
                v-if="user.id === $store.state.user.id"
              ></label>
              <input
                id="upload-dp"
                v-if="user.id === $store.state.user.id"
                ref="imagefile"
                type="file"
                accept="image/*"
                @change="preview"
              />
              </div>

              <div id="dp-buttons" v-if="item.imageUrl">
                <button
                  id="postpicture"
                  class="badge bg-info"
                  v-on:click.prevent="postPicture"
                >
                  Post</button
                >--to .prevent default function of click--
                <button
                  id="cancelpost"
                  class="badge bg-secondary"
                  v-on:click.prevent="reset"
                >
                  Cancel
                </button>
                <div>
                  <label
                    class="progress-bar bg-info progress-bar-striped"
                    style="width: 40%"
                    v-if="!gotResponse && postRequestSent"
                    >Uploading... Please wait...</label
                  >
                  <label
                    style="color: blue; font-weight: bold"
                    v-else-if="gotResponse"
                    >Picture uploaded!</label
                  >
                </div>
                
            </div>
            
              <div class="bg-light p-4 d-flex justify-content-end text-center">
                <ul class="list-inline mb-0">
                  <li class="list-inline-item">
                    <h5 class="font-weight-bold mb-0 d-block">215</h5>
                    <small class="text-muted">
                      <i class="fas fa-image mr-1"></i>Photos</small
                    >
                  </li>
                  <li class="list-inline-item">
                    <h5 class="font-weight-bold mb-0 d-block">745</h5>
                    <small class="text-muted">
                      <i class="fas fa-user mr-1"></i>Followers</small
                    >
                  </li>
                  <li class="list-inline-item">
                    <h5 class="font-weight-bold mb-0 d-block">340</h5>
                    <small class="text-muted">
                      <i class="fas fa-user mr-1"></i>Following</small
                    >
                  </li>
                </ul>
              </div>
              <div class="py-4 px-4" style="background-color: lightgray">
                <div
                  class="d-flex align-items-center justify-content-between mb-3"
                >
                  <h5 class="mb-0">Recent photos</h5>
                </div>
                <div class="row">
                  <div class="col-lg-6 mb-2 pr-lg-1">
                    <img
                      src="https://images.unsplash.com/photo-1469594292607-7bd90f8d3ba4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"
                      alt=""
                      class="img-fluid rounded shadow-sm"
                    />
                  </div>
                  <div class="col-lg-6 mb-2 pl-lg-1">
                    <img
                      src="https://images.unsplash.com/photo-1493571716545-b559a19edd14?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"
                      alt=""
                      class="img-fluid rounded shadow-sm"
                    />
                  </div>
                  <div class="col-lg-6 pr-lg-1 mb-2">
                    <img
                      src="https://images.unsplash.com/photo-1453791052107-5c843da62d97?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80"
                      alt=""
                      class="img-fluid rounded shadow-sm"
                    />
                  </div>
                  <div class="col-lg-6 pl-lg-1">
                    <img
                      src="https://images.unsplash.com/photo-1475724017904-b712052c192a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80"
                      alt=""
                      class="img-fluid rounded shadow-sm"
                    />
                  </div>
                </div>
              </div>

              <div id="remove-post" v-if="user.id === $store.state.user.id">
                <button @click.prevent="deletePosts">
                  Delete Selected Posts
                </button>
              </div>
            </div>
          
        
    </div>
  </div>
</template>

<script>
import Header from "./Header.vue";
import photoService from "../services/PhotoService.js";

export default {
  name: "userprofile",
  components: {
    Header,
  },
  data() {
    return {
      userId: null,
      user: {
        id: null,
        email: null,
        firstName: null,
        lastName: null,
        profileImage: null,
      },
      item: {
        //...
        image: null,
        imageUrl: null,
        caption: null,
      },
      gotResponse: false,
      postRequestSent: false,
    };
  },
  created() {
    this.userId = this.$route.params.userId;
    photoService.getUser(this.userId).then((response) => {
      const data = response.data;
      this.user.id = data.id;
      this.user.email = data.email;
      this.user.firstName = data.firstName;
      this.user.lastName = data.lastName;
      this.user.profileImage = data.profileImage;
    });
  },
  methods: {
    preview(event) {
      const file = event.target.files[0]; //to retrieve data from event
      this.item.image = file;
      this.item.imageUrl = URL.createObjectURL(file);
    },
    postPicture() {
      this.postRequestSent = true;
      photoService
        .addDisplayPicture(this.item.image)
        .then((response) => {
          if (response.status === 200) {
            this.gotResponse = true;
          }
        })
        .catch((error) => {
          alert(error.response.status);
          alert("Photo upload failed");
        });
    },
    deletePosts() {
      photoService
        .deletePosts(this.$store.state.postsToDelete)
        .then((response) => {
          if (response.status === 204) {
            this.$store.state.postsToDelete.forEach((post) => {
              this.$store.commit("REMOVE_POST_TO_DELETE", post);
            });
            this.$store.commit('RESET_POSTS_TO_DELETE');
            }
        
    });
  },
    
        reset(){
          this.item.image = null,
          this.item.imageUrl = null,
          this.item.caption = null,
          this.gotResponse = false,
          this.postRequestSent = false;
        }
  },
};
   


</script>

<style>
.profile-head {
  transform: translateY(5rem);
}

.cover {
  background-image: url(https://images.unsplash.com/photo-1530305408560-82d13781b33a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1352&q=80);
  background-size: cover;
  background-repeat: no-repeat;
}

body {
  background: rgb(46,107,152);
background: linear-gradient(0deg, rgba(46,107,152,0.9814385150812065) 5%, rgba(0,212,255,1) 100%);
  min-height: 100vh;
  overflow-x: hidden;
}
/* #remove-post {
    grid-area: posts;
}

#user-profile{
    display: grid;
    height: 100vh;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 2fr 4fr 4fr 4fr 4fr 4fr;
    grid-template-areas: "profile-header profile-header" 
                         "profile-pic ."
                         "profile-pic user-detail"
                         "profile-pic ."
                         "upload ."
                         "posts posts";
}


.upload-label-button {
    display:flex;
    grid-area: upload;
    justify-content: center;
}

.upload-label-button #upload-label {
    grid-area: upload;
    display: flex;
}
 #profile-pic {
     border: 2px;
     border-color: black;
 }
*/
#profile-header {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
  grid-area: profile-header;
  font-family: "Billabong";
  background-color: aliceblue;
  width: 100%;
  
}
/*
#profile-pic {
    margin-top: 10%;
    margin-left: 15%;
    grid-area: profile-pic;
    align-self: center;
}
#user-detail {
    grid-area: user-detail;
    align-self: center;
}
#posts {
    grid-area: posts;
}
#upload-dp{
    grid-area: upload;
    display: flex;
    justify-content: center;;
    flex-direction: column;
    align-items: flex-end;
    
}
#upload-label{
    
    grid-area: upload;
    display:flex;
    margin-left: 30%;
    margin-bottom: 10px;
    align-items: flex-start;
    align-self: center;
}
#dp-buttons{
    grid-area: upload;
    margin-left: 30%;
    margin-top: 10px;
    align-self: center;
} */
</style>

<!--
 <div id="user-profile">
            <h1 id="profile-header">
              <Header></Header>
            </h1>
        <div class="row py-5 px-4"> 
            <div class="col-md-5 mx-auto">
                -- Profile --
                <div class="bg-white shadow rounded overflow-hidden">
                    <div class="px-4 pt-0 pb-4 cover">
                        <div class="media align-items-end profile-head">
                            
                    <div id="profile-pic" v-if="item.imageUrl">
                    <img :src="item.imageUrl"/>
                </div>
             <div id="profile-pic" v-else-if="user.profileImage">
                <img :src="user.profileImage"/>
            </div> 
            <div id="profile-pic" v-else>
                <img class="rounded-circle mt-5" src="../../public/default_profile_picture.jpeg"/>
            </div>
            
                <label id="upload-label" v-if="user.id===$store.state.user.id"></label>
                <input id="upload-dp" v-if="user.id===$store.state.user.id" ref="imagefile" type="file" accept="image/*" @change="preview"/>
            
            <div id="dp-buttons" v-if="item.imageUrl">
                <button id="postpicture" class="badge bg-info" v-on:click.prevent="postPicture">Post</button><--to .prevent default function of click--
                <button id="cancelpost" class="badge bg-secondary" v-on:click.prevent="reset">Cancel</button>
                <div>
                    <label class="progress-bar bg-info progress-bar-striped" style= "width:40%" v-if="!gotResponse && postRequestSent">Uploading... Please wait...</label>
                    <label style="color: blue; font-weight: bold" v-else-if="gotResponse">Picture uploaded!</label>
                </div> 
            </div>
            <div id="user-detail">
                <div>User Id : {{user.id}}</div>
                <div>Username : {{user.username}}</div>
                <div>First Name : {{user.firstName}}</div>
                <div>Last Name : {{user.lastName}}</div>
                <div>email : {{user.email}}</div>
                <div>{{user.firstName}} {{user.lastName}}'s Posts:</div>
            </div>
            <div id="remove-post" v-if="user.id===$store.state.user.id">
                <button @click.prevent="deletePosts">Delete Selected Posts</button>
            </div>
            </div>
            </div>
            </div>
            </div>
        </div> 
    </div>



 <div id="profile-pic" v-else-if="user.profileImage">
                <img :src="user.profileImage"/>
            </div> -->
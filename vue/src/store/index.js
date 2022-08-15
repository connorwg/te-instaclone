import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if (currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    active: 'home',
    
    icon2: 'https://th.bing.com/th/id/As7xDOIkJIOgEcA480x360?&rs=1&pid=ImgDet',
    isLiked: false,
    token: currentToken || '',
    user: currentUser || {},//at the time of login or authentication this user's info is getting stored in this property
    currentPostId: -1,
    
    images: [{
      id: '1',
      userId: "11",
      firstName: "Prachi",
      lastName: "lastName",
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03E9U67GNL-bd5c7423ccc1-512",
      description: 'photo description here',
      timeStamp: '',
      likes: [7, 8, 11],
      comments: ['blah  comments blah', 'comments blah', 'more comments']
    },
    {
      id: '2',
      userId: "12",
      firstName: "Connor",
      lastName: "lastName",
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03B3TBG8HK-c6b48e687235-512",
      description: 'photo description here',
      timeStamp: '',
      likes: [9, 2],
      comments: ['blah comments comments blah', 'comments', 'extra comments']
    }, {
      id: '3',
      userId: "13",
      firstName: "Andrew",
      lastName: "lastName",
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03DK8YV9LZ-3d43d76e3219-512",
      description: 'photo description here',
      timeStamp: '',
      likes: [11, 3, 6, 67],
      comments: ['blah blah comments comments', 'comments blah blah']
    }, {
      id: '4',
      userId: "14",
      firstName: "Dave",
      lastName: "lastName",
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03B3T73DSR-9631f652a35e-512",
      description: 'photo description here',
      timeStamp: '',
      likes: [6],
      comments: ['blah comments blah', 'comments']
    }

    ],
    currentImage: {
      id: "4",
      userID: "14",
      firstName: "Dave",
      lastName: "lastName",
      picture: "https://ca.slack-edge.com/T0GNFLF6D-U03B3T73DSR-9631f652a35e-512",
      description: "this is the photo description",
      timestamp: "",
      likes: [6],
      comments: ['blah comments blah', 'comments', 'more than 2 comments', 'last comment']

    }

  },
  mutations: {
    SET_TAB(state, active) {
      state.active = active;
    },
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_PHOTOS(state, data) {
      state.images = data;
    },
    SET_CURRENT_PHOTO(state, data) {
      alert(data.id);
      state.currentImage = data;
      alert(state.currentImage.id);
    },
    ADD_PHOTOS(state, data) {
      let image = {
        id: data.post_id,
        userId: data.user_id,
        firstName: '',
        lastName: '',
        picture: data.pictureLink,
        description: data.description,
        timeStamp: data.timestamp,
        likes: [],
        comments: []
      }
      image.firstName = state.user.firstName;
      image.lastName = state.user.lastName;
      state.images.unshift(image);
    },
    SET_PHOTO_LIKES(state, data) {
      let photoToUpdate = state.images.findIndex(photo => {
         return photo.id == data.id
      });
      state.images[photoToUpdate].likes= data.likes;
    
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user', JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    ADD_COMMENT(state, commentObj){
      state.images.forEach(image => {
        if(image.id === commentObj.postId){
          image.comments.unshift(commentObj.comment);
        }
      });
    }
  }
})

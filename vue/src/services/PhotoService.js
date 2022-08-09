import axios from 'axios';

export default {

  getPhotos() {
    return axios.get('/images');
  },

  

}
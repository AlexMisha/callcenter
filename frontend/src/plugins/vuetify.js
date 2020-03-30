import Vue from "vue";
import Vuetify from "vuetify/lib";
import "material-design-icons-iconfont/dist/material-design-icons.css";

Vue.use(Vuetify);

export default new Vuetify({
  theme: {
    themes: {
      light: {
        primary: "#3f51b5",
        secondary: "#03a9f4",
        accent: "#2196f3",
        error: "#f44336",
        warning: "#ffeb3b",
        info: "#4caf50",
        success: "#8bc34a"
      }
    }
  },
  iconfont: "md"
});


import StackNavigator from "react-navigation/lib-rn/navigators/StackNavigator";
import Main from "./components/Main";
import NovelDetail from "./components/NovelDetail";
import CreateNovel from "./components/CreateNovel";
const ModalStack=StackNavigator({
    Home:{
        screen:App
    },
    Main: {
        screen: Main
    },
    NovelDetail: {
        screen: NovelDetail,
    },
    Create: {
        screen: CreateNovel,
    }
});
export default ModalStack;


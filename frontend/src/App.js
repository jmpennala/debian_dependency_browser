import Deps from "./Deps";

import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Deps />}>
          <Route index path="/:dep" element={<Deps />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;

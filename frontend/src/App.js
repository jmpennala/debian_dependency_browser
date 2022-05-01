import Deps from "./Deps";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import "./App.css";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Deps />}>
            <Route index path="/:dep" element={<Deps />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

import React, { useEffect, useState } from "react";
import { Router, Route, useParams } from "react-router";
import { Link } from "react-router-dom";
import DepInfo from "./DepInfo";
import DepsList from "./DepsList";

const Deps = () => {
  const { dep } = useParams();

  const [deps, setDeps] = useState();

  useEffect(() => {
    const fetchDeps = async () => {
      const result = await fetch("http://localhost:4040/api/deps");
      const json = await result.json();
      json?.sort(
        (d1, d2) => d1.package.codePointAt() - d2.package.codePointAt()
      );
      setDeps(json);
    };

    fetchDeps();
  }, []);

  const selectedDep = dep ? deps?.filter((d) => d.package === dep)[0] : null;

  return (
    <div>
      {deps && !selectedDep && <DepsList deps={deps} />}
      {selectedDep && <DepInfo dep={selectedDep} />}
    </div>
  );
};

export default Deps;

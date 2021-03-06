import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import DepInfo from "./DepInfo";
import DepsList from "./DepsList";

const Deps = () => {
  const { dep } = useParams();

  const [deps, setDeps] = useState({ deps: [], reverseDeps: {} });

  useEffect(() => {
    const fetchDeps = async () => {
      const result = await fetch("http://localhost:4040/api/deps");
      const json = await result.json();

      // sort to alphabetical order by package name
      // FIXME: bug in sorting
      json?.sort((d1, d2) => (d1.package < d2.package ? -1 : 1));

      const reverseDeps = json?.reduce((reverseDeps, dep) => {
        if (dep.depends) {
          dep.depends.forEach((d) => {
            const depsArr = reverseDeps[d]
              ? [...reverseDeps[d], dep.package]
              : [dep.package];
            reverseDeps[d] = depsArr;
          });
        }

        return reverseDeps;
      }, {});

      setDeps({ deps: json, reverseDeps });
    };

    fetchDeps();
  }, []);

  const selectedDep = dep ? deps?.deps?.find((d) => d.package === dep) : null;

  return (
    <div>
      {deps && !selectedDep && <DepsList deps={deps.deps} />}
      {selectedDep && (
        <DepInfo
          dep={selectedDep}
          reverseDeps={deps?.reverseDeps[selectedDep?.package]}
        />
      )}
    </div>
  );
};

export default Deps;

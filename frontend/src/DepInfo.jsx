import { Link } from "react-router-dom";

const DependenciesLinks = ({ dependencies }) =>
  dependencies?.map((dependency) => (
    <div key={dependency}>
      <Link to={dependency}>{dependency}</Link>
    </div>
  ));

const DepInfo = ({ dep, reverseDeps }) => (
  <>
    <Link to="/">Back to list</Link>
    <h1>Package: {dep.package}</h1>
    <div>{dep.short_description}</div>
    <div>
      <h2>Full description</h2>
      {dep.description}
    </div>
    <div>
      <h2>Dependencies</h2>
      <DependenciesLinks dependencies={dep.depends} />
    </div>
    <div>
      <h2>Those who depend on this dependency</h2>
      <DependenciesLinks dependencies={reverseDeps} />
    </div>
  </>
);

export default DepInfo;

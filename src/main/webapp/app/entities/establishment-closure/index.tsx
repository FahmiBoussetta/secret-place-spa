import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EstablishmentClosure from './establishment-closure';
import EstablishmentClosureDetail from './establishment-closure-detail';
import EstablishmentClosureUpdate from './establishment-closure-update';
import EstablishmentClosureDeleteDialog from './establishment-closure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EstablishmentClosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EstablishmentClosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EstablishmentClosureDetail} />
      <ErrorBoundaryRoute path={match.url} component={EstablishmentClosure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EstablishmentClosureDeleteDialog} />
  </>
);

export default Routes;

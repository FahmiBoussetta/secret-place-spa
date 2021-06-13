import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HousingClosure from './housing-closure';
import HousingClosureDetail from './housing-closure-detail';
import HousingClosureUpdate from './housing-closure-update';
import HousingClosureDeleteDialog from './housing-closure-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HousingClosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HousingClosureUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HousingClosureDetail} />
      <ErrorBoundaryRoute path={match.url} component={HousingClosure} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HousingClosureDeleteDialog} />
  </>
);

export default Routes;

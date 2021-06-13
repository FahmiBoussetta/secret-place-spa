import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './manage.reducer';
import { IManage } from 'app/shared/model/manage.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IManageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Manage = (props: IManageProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { manageList, match, loading } = props;
  return (
    <div>
      <h2 id="manage-heading" data-cy="ManageHeading">
        <Translate contentKey="secretplacespaApp.manage.home.title">Manages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="secretplacespaApp.manage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="secretplacespaApp.manage.home.createLabel">Create new Manage</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {manageList && manageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="secretplacespaApp.manage.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.manage.userId">User Id</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.manage.establishmentId">Establishment Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {manageList.map((manage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${manage.id}`} color="link" size="sm">
                      {manage.id}
                    </Button>
                  </td>
                  <td>{manage.userId ? manage.userId.id : ''}</td>
                  <td>
                    {manage.establishmentId ? (
                      <Link to={`establishment/${manage.establishmentId.id}`}>{manage.establishmentId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${manage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${manage.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${manage.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="secretplacespaApp.manage.home.notFound">No Manages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ manage }: IRootState) => ({
  manageList: manage.entities,
  loading: manage.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Manage);

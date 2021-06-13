import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './establishment.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablishmentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Establishment = (props: IEstablishmentProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { establishmentList, match, loading } = props;
  return (
    <div>
      <h2 id="establishment-heading" data-cy="EstablishmentHeading">
        <Translate contentKey="secretplacespaApp.establishment.home.title">Establishments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="secretplacespaApp.establishment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="secretplacespaApp.establishment.home.createLabel">Create new Establishment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {establishmentList && establishmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.address">Address</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.latitude">Latitude</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.longitude">Longitude</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.globalRate">Global Rate</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.establishmentType">Establishment Type</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.hasParking">Has Parking</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.hasRestaurant">Has Restaurant</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.hasFreeWifi">Has Free Wifi</Translate>
                </th>
                <th>
                  <Translate contentKey="secretplacespaApp.establishment.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {establishmentList.map((establishment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${establishment.id}`} color="link" size="sm">
                      {establishment.id}
                    </Button>
                  </td>
                  <td>{establishment.name}</td>
                  <td>{establishment.address}</td>
                  <td>{establishment.latitude}</td>
                  <td>{establishment.longitude}</td>
                  <td>{establishment.globalRate}</td>
                  <td>
                    <Translate contentKey={`secretplacespaApp.EstablishmentType.${establishment.establishmentType}`} />
                  </td>
                  <td>{establishment.hasParking ? 'true' : 'false'}</td>
                  <td>{establishment.hasRestaurant ? 'true' : 'false'}</td>
                  <td>{establishment.hasFreeWifi ? 'true' : 'false'}</td>
                  <td>{establishment.description}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${establishment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${establishment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${establishment.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="secretplacespaApp.establishment.home.notFound">No Establishments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ establishment }: IRootState) => ({
  establishmentList: establishment.entities,
  loading: establishment.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Establishment);

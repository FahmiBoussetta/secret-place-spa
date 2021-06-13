import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './establishment.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablishmentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const EstablishmentDetail = (props: IEstablishmentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { establishmentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="establishmentDetailsHeading">
          <Translate contentKey="secretplacespaApp.establishment.detail.title">Establishment</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{establishmentEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="secretplacespaApp.establishment.name">Name</Translate>
            </span>
            <UncontrolledTooltip target="name">
              <Translate contentKey="secretplacespaApp.establishment.help.name" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.name}</dd>
          <dt>
            <span id="address">
              <Translate contentKey="secretplacespaApp.establishment.address">Address</Translate>
            </span>
            <UncontrolledTooltip target="address">
              <Translate contentKey="secretplacespaApp.establishment.help.address" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.address}</dd>
          <dt>
            <span id="latitude">
              <Translate contentKey="secretplacespaApp.establishment.latitude">Latitude</Translate>
            </span>
            <UncontrolledTooltip target="latitude">
              <Translate contentKey="secretplacespaApp.establishment.help.latitude" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.latitude}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="secretplacespaApp.establishment.longitude">Longitude</Translate>
            </span>
            <UncontrolledTooltip target="longitude">
              <Translate contentKey="secretplacespaApp.establishment.help.longitude" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.longitude}</dd>
          <dt>
            <span id="globalRate">
              <Translate contentKey="secretplacespaApp.establishment.globalRate">Global Rate</Translate>
            </span>
            <UncontrolledTooltip target="globalRate">
              <Translate contentKey="secretplacespaApp.establishment.help.globalRate" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.globalRate}</dd>
          <dt>
            <span id="establishmentType">
              <Translate contentKey="secretplacespaApp.establishment.establishmentType">Establishment Type</Translate>
            </span>
            <UncontrolledTooltip target="establishmentType">
              <Translate contentKey="secretplacespaApp.establishment.help.establishmentType" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.establishmentType}</dd>
          <dt>
            <span id="hasParking">
              <Translate contentKey="secretplacespaApp.establishment.hasParking">Has Parking</Translate>
            </span>
            <UncontrolledTooltip target="hasParking">
              <Translate contentKey="secretplacespaApp.establishment.help.hasParking" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.hasParking ? 'true' : 'false'}</dd>
          <dt>
            <span id="hasRestaurant">
              <Translate contentKey="secretplacespaApp.establishment.hasRestaurant">Has Restaurant</Translate>
            </span>
            <UncontrolledTooltip target="hasRestaurant">
              <Translate contentKey="secretplacespaApp.establishment.help.hasRestaurant" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.hasRestaurant ? 'true' : 'false'}</dd>
          <dt>
            <span id="hasFreeWifi">
              <Translate contentKey="secretplacespaApp.establishment.hasFreeWifi">Has Free Wifi</Translate>
            </span>
            <UncontrolledTooltip target="hasFreeWifi">
              <Translate contentKey="secretplacespaApp.establishment.help.hasFreeWifi" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.hasFreeWifi ? 'true' : 'false'}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="secretplacespaApp.establishment.description">Description</Translate>
            </span>
            <UncontrolledTooltip target="description">
              <Translate contentKey="secretplacespaApp.establishment.help.description" />
            </UncontrolledTooltip>
          </dt>
          <dd>{establishmentEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/establishment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/establishment/${establishmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ establishment }: IRootState) => ({
  establishmentEntity: establishment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(EstablishmentDetail);

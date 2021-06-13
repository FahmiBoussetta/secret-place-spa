import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './housing-template.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHousingTemplateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HousingTemplateDetail = (props: IHousingTemplateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { housingTemplateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="housingTemplateDetailsHeading">
          <Translate contentKey="secretplacespaApp.housingTemplate.detail.title">HousingTemplate</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{housingTemplateEntity.id}</dd>
          <dt>
            <span id="housingType">
              <Translate contentKey="secretplacespaApp.housingTemplate.housingType">Housing Type</Translate>
            </span>
            <UncontrolledTooltip target="housingType">
              <Translate contentKey="secretplacespaApp.housingTemplate.help.housingType" />
            </UncontrolledTooltip>
          </dt>
          <dd>{housingTemplateEntity.housingType}</dd>
          <dt>
            <span id="nbOfUnit">
              <Translate contentKey="secretplacespaApp.housingTemplate.nbOfUnit">Nb Of Unit</Translate>
            </span>
            <UncontrolledTooltip target="nbOfUnit">
              <Translate contentKey="secretplacespaApp.housingTemplate.help.nbOfUnit" />
            </UncontrolledTooltip>
          </dt>
          <dd>{housingTemplateEntity.nbOfUnit}</dd>
          <dt>
            <span id="nbMaxOfOccupants">
              <Translate contentKey="secretplacespaApp.housingTemplate.nbMaxOfOccupants">Nb Max Of Occupants</Translate>
            </span>
            <UncontrolledTooltip target="nbMaxOfOccupants">
              <Translate contentKey="secretplacespaApp.housingTemplate.help.nbMaxOfOccupants" />
            </UncontrolledTooltip>
          </dt>
          <dd>{housingTemplateEntity.nbMaxOfOccupants}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="secretplacespaApp.housingTemplate.price">Price</Translate>
            </span>
            <UncontrolledTooltip target="price">
              <Translate contentKey="secretplacespaApp.housingTemplate.help.price" />
            </UncontrolledTooltip>
          </dt>
          <dd>{housingTemplateEntity.price}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.housingTemplate.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{housingTemplateEntity.establishmentId ? housingTemplateEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/housing-template" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/housing-template/${housingTemplateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ housingTemplate }: IRootState) => ({
  housingTemplateEntity: housingTemplate.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HousingTemplateDetail);

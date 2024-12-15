import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('SubscriptionType e2e test', () => {
  const subscriptionTypePageUrl = '/subscription-type';
  const subscriptionTypePageUrlPattern = new RegExp('/subscription-type(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const subscriptionTypeSample = { label: 'extrêmement sur approximativement' };

  let subscriptionType;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/subscription-types+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/subscription-types').as('postEntityRequest');
    cy.intercept('DELETE', '/api/subscription-types/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (subscriptionType) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/subscription-types/${subscriptionType.id}`,
      }).then(() => {
        subscriptionType = undefined;
      });
    }
  });

  it('SubscriptionTypes menu should load SubscriptionTypes page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('subscription-type');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SubscriptionType').should('exist');
    cy.url().should('match', subscriptionTypePageUrlPattern);
  });

  describe('SubscriptionType page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(subscriptionTypePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SubscriptionType page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/subscription-type/new$'));
        cy.getEntityCreateUpdateHeading('SubscriptionType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subscriptionTypePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/subscription-types',
          body: subscriptionTypeSample,
        }).then(({ body }) => {
          subscriptionType = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/subscription-types+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/subscription-types?page=0&size=20>; rel="last",<http://localhost/api/subscription-types?page=0&size=20>; rel="first"',
              },
              body: [subscriptionType],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(subscriptionTypePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SubscriptionType page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('subscriptionType');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subscriptionTypePageUrlPattern);
      });

      it('edit button click should load edit SubscriptionType page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SubscriptionType');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subscriptionTypePageUrlPattern);
      });

      it('edit button click should load edit SubscriptionType page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SubscriptionType');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subscriptionTypePageUrlPattern);
      });

      it('last delete button click should delete instance of SubscriptionType', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('subscriptionType').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', subscriptionTypePageUrlPattern);

        subscriptionType = undefined;
      });
    });
  });

  describe('new SubscriptionType page', () => {
    beforeEach(() => {
      cy.visit(`${subscriptionTypePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SubscriptionType');
    });

    it('should create an instance of SubscriptionType', () => {
      cy.get(`[data-cy="label"]`).type('orange');
      cy.get(`[data-cy="label"]`).should('have.value', 'orange');

      cy.get(`[data-cy="summary"]`).type('propre hôte');
      cy.get(`[data-cy="summary"]`).should('have.value', 'propre hôte');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        subscriptionType = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', subscriptionTypePageUrlPattern);
    });
  });
});

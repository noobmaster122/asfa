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

describe('Member e2e test', () => {
  const memberPageUrl = '/member';
  const memberPageUrlPattern = new RegExp('/member(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const memberSample = {
    firstName: 'Évangéline',
    lastName: 'Paul',
    email: 'Parfait.Arnaud67@hotmail.fr',
    country: 'Angola',
    city: 'Besançon',
    address: 'parfois',
    zipCode: '59083',
    birthDate: '2024-12-13',
    signupDate: '2024-12-13',
    rank: 'SPOUSE',
  };

  let member;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/members+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/members').as('postEntityRequest');
    cy.intercept('DELETE', '/api/members/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (member) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/members/${member.id}`,
      }).then(() => {
        member = undefined;
      });
    }
  });

  it('Members menu should load Members page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('member');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Member').should('exist');
    cy.url().should('match', memberPageUrlPattern);
  });

  describe('Member page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(memberPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Member page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/member/new$'));
        cy.getEntityCreateUpdateHeading('Member');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', memberPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/members',
          body: memberSample,
        }).then(({ body }) => {
          member = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/members+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/members?page=0&size=20>; rel="last",<http://localhost/api/members?page=0&size=20>; rel="first"',
              },
              body: [member],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(memberPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Member page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('member');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', memberPageUrlPattern);
      });

      it('edit button click should load edit Member page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Member');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', memberPageUrlPattern);
      });

      it('edit button click should load edit Member page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Member');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', memberPageUrlPattern);
      });

      it('last delete button click should delete instance of Member', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('member').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', memberPageUrlPattern);

        member = undefined;
      });
    });
  });

  describe('new Member page', () => {
    beforeEach(() => {
      cy.visit(`${memberPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Member');
    });

    it('should create an instance of Member', () => {
      cy.get(`[data-cy="memberUID"]`).type('ce45e81b-a5c0-4e0c-91e0-9b726066e219');
      cy.get(`[data-cy="memberUID"]`).invoke('val').should('match', new RegExp('ce45e81b-a5c0-4e0c-91e0-9b726066e219'));

      cy.get(`[data-cy="firstName"]`).type('Paule');
      cy.get(`[data-cy="firstName"]`).should('have.value', 'Paule');

      cy.get(`[data-cy="lastName"]`).type('Lemoine');
      cy.get(`[data-cy="lastName"]`).should('have.value', 'Lemoine');

      cy.get(`[data-cy="middleName"]`).type('au-dehors hebdomadaire');
      cy.get(`[data-cy="middleName"]`).should('have.value', 'au-dehors hebdomadaire');

      cy.get(`[data-cy="email"]`).type('Basile_Lefevre@gmail.com');
      cy.get(`[data-cy="email"]`).should('have.value', 'Basile_Lefevre@gmail.com');

      cy.get(`[data-cy="country"]`).type('Arménie');
      cy.get(`[data-cy="country"]`).should('have.value', 'Arménie');

      cy.get(`[data-cy="city"]`).type('Perpignan');
      cy.get(`[data-cy="city"]`).should('have.value', 'Perpignan');

      cy.get(`[data-cy="address"]`).type('boum au-dedans de bang');
      cy.get(`[data-cy="address"]`).should('have.value', 'boum au-dedans de bang');

      cy.get(`[data-cy="zipCode"]`).type('71699');
      cy.get(`[data-cy="zipCode"]`).should('have.value', '71699');

      cy.get(`[data-cy="birthDate"]`).type('2024-12-13');
      cy.get(`[data-cy="birthDate"]`).blur();
      cy.get(`[data-cy="birthDate"]`).should('have.value', '2024-12-13');

      cy.get(`[data-cy="signupDate"]`).type('2024-12-13');
      cy.get(`[data-cy="signupDate"]`).blur();
      cy.get(`[data-cy="signupDate"]`).should('have.value', '2024-12-13');

      cy.get(`[data-cy="rank"]`).select('CHILDREN');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        member = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', memberPageUrlPattern);
    });
  });
});

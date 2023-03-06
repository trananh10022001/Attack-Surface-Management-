import pytest
import unittest

from modules.sfp_cinsscore import sfp_cinsscore
from sflib import SpiderFoot


@pytest.mark.usefixtures
class TestModuleCinsscore(unittest.TestCase):

    def test_opts(self):
        module = sfp_cinsscore()
        self.assertEqual(len(module.opts), len(module.optdescs))

    def test_setup(self):
        sf = SpiderFoot(self.default_options)
        module = sfp_cinsscore()
        module.setup(sf, dict())

    def test_watchedEvents_should_return_list(self):
        module = sfp_cinsscore()
        self.assertIsInstance(module.watchedEvents(), list)

    def test_producedEvents_should_return_list(self):
        module = sfp_cinsscore()
        self.assertIsInstance(module.producedEvents(), list)
